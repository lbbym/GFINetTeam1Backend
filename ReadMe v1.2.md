# Citi GFINet Project Note

## 1 三方要素 & 2 Legs

1. Trader -> Sales
2. Sales -> Clients
3. Clients

* 对应Application
1. Trade Workstation
2. Sales Workstation

* 2 Legs
1. Trader -> Sales
2. Sales -> Clients

* Trader Overwrite Sales

本项目应以TW为主，SW为辅
SW可简化为只要有一个Ticket Entry Window + Submit选项 （即Import Trade操作）

## 2 DB Table Design

### 2.1 product

* 10项数据 （单利）

1. Cusip - 债券标识符 (9位数字 + 字母)
2. Face Value - 票面价值 (e.g. 100$)
3. Coupon - 票面利率 (e.g. 4%)
4. Maturity Date - 到期日 (e.g. 2029.10.16)

### 2.2 User

和其他表的关联最低要求：输入目标Sys(TradeDestSys)后能显示当前Client Name，或者输入后能自动fetch后续信息

#### 2.2.1 users

id, name, password, role_id

#### 2.2.2 roles

role_id, role

### 2.3 Trader & Sales

#### 2.3.1 trader_deals

TxnI, Cusip(Product_ID), Volume, Price, Notional_Principal, TradeOrigSys, TradeDestSys, Timestamp, InterOrigSys, InterI, InterVNum, Ver(Version), Status, RejectCode, RejectReason

* 新增
  1. Notional_Principal -  名义本金，即Volume * Price
  2. TradeDestSys - 记录Trader Leg的Destination，即Sales
  3. Timestamp - 当前TL记录的时间戳

PK: TxnI

FK: users - id

* Elements displayed on the front end

  TxnI, Cusip(Product_ID), Volume, Price, Notional_Principal, (Timestamp),  Ver, Status

#### 2.3.2 sales_deals

TxnI, Cusip(Product_ID), Volume, Price, Notional_Principal, TradeOrigSys, TradeDestSys, Timestamp, InterOrigSys, InterI, InterVNum, Ver(Version), Status, RejectCode, RejectReason

* 新增
  1. Notional_Principal -  名义本金，即Volume * Price
  2. TradeDestSys - 记录Trader Leg的Destination，即Trader
  3. Timestamp - 当前SL记录的时间戳

PK: TxnI

FK: users - id

* Elements displayed on the front end

  TxnI, Cusip(Product_ID), Volume, Price, Notional_Principal, (Timestamp),  Ver, Status

## 3 Trade Workstation概要

智能化Match - AutoMatch功能，AutoMatch同时自动校验内部数据一致性

* 若能成功Match，最终发送到Back Office(Back Office仅仅为Simulator，发送Accept / Rejected / Held)

### 3.1 Match判定

* 使用两方共同的信息联合判断 （存在陷阱？？？？）

若TradeDestSys + Cusip + Volume + Price 完全一致 -> 2 Trades Match

* 实际：由TradeDestSys -> Cusip -> Volume -> Price，逐渐缩小Auto Match范围
* TradeOrigSys / TradeDestSys实际记录信息："S1111", "T1111"

### 3.2 Log In界面

Trader & Sales复用同一登录/创建界面，根据用户名关键字判断类型 / DB roles表查询role (e.g. T1111 -> Trader, S2222 -> Sales)

创建新用户界面仅提供创建Trader接口，不提供创建Sales接口

* 不同Trader不能查看彼此信息，必须登录才能使用查看功能
* DB内置唯一Sales账号，写死不做修改

### 3.3 MISC

1. 查询列表界面需要列出当前收到的全部Trades，至少分为History已成功Match的历史Trade记录，In Process当前Trade未能找到相应的Match记录，Auto-Match Failed数据存在不一致的Trade记录，共3大部分
2. 若AutoMatch失败（校验错误） --> 提示并提供手动Match选项（Trader Overwrite Sales）
4. 提供New Trade新增选项 --> 弹出Entry Window手动输入Trade信息(import trade操作)（提供复用 - 新建 / 修改功能）
4. Audit Blotter全称跟踪**全部历史数据** - **Trade** + **Matching Leg**

## 4 MISC

开发时考虑以下特性：

1. 日志与异常 - 记录***全部***操作和信息
2. 安全性
3. 运维优先 - 后续更新、扩展可能
4. 高并发
5. 浏览器适用性
6. 依赖注入 - 快速导入导出，简化业务的工厂解耦


## 5 已解决问题记录

1. Trader和Sales的Ticket / ID匹配问题

   联合判断，注意陷阱！！！

2. TW & SW 的2 Legs匹配模式 - 1:1

3. Execution Transaction Entity表格前5项理解问题
   1. TradeOrigSys - 创建方

   2. TxnI - 和问1中Ticket的本质是否相同？是否可以直接使用？

      由系统生成，归属于Trade独特的ID，不同于匹配判断标识

   3. InterOrigSys - Interaction定义？任何操作都属于Interaction？仅改变数据的操作为Interaction？

      仅create / edit操作可归为Interaction，match操作不纳入其中

      * 本质：操作产生了数据的变化（新建 / 修改等）

   4. InterI - 记录Interaction次数

      I1V1 Requested -> Pending -> TPS Processed -> Status

   5. InterVNum
   
4. Product表是否仅作数据储存？ 否

   输入Trade时需进行查询并显示相关的Product Info
   
5. 当前Trade（即Transaction Entity）仅于BO 走完后才可Unlock，允许手动修改

## 6 极端测试情况

Situation - 原定105，TW错设为104，SW错设为101

Work Flow - TL & SL均进入到Pending阶段，无法Auto Matching，提供手动选项Eddit，T复写S，TL & SL前进到了TPS Pro -> BO。BO过程结束后解锁TL SL，再次允许Trader手动修改Price，修改后自动覆盖Sales的Price。

* 详见17日答疑后完整流程解析

## 7 已知问题

1. DB开销问题

   Buffer Pool更新状态：Requested, Pending, TPS_Processed, ACCPTED/REJECTED

   DBz更新状态：Requested, Pending

2. Matching过程FIFO队列多线程实现

   两个for？单个for？