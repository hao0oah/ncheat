--CREATE DATABASE ncheat DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

--个人客户信息表
create table grInfo 
  (
    fn20khbh char(20) not null ,
    fn20zjlx char(1) not null ,
    fn20zjbh char(30) not null ,
    fn20xm char(60) not null ,
    fn20xmpy char(30) not null ,
    fn20xb integer not null ,
    fn20csrq date not null ,
    fn20mz char(2) not null ,
    fn20gjdm char(3) not null ,
    fn20dw char(30) not null ,
    fn20zw char(20) not null ,
    fn20gzzh char(8) not null ,
    fn20yddh char(15) not null ,
    fn20chjh char(20) not null ,
    fn20email char(40) not null ,
    fn20dwbh char(20) not null ,
    fn20zy char(2) not null ,
    fn20dwdz char(60) not null ,
    fn20dwyb integer not null ,
    fn20dwdh char(20) not null ,
    fn20zzdz char(60) not null ,
    fn20zzyb integer not null ,
    fn20zzdh char(20) not null ,
    fn20dzdyb integer not null ,
    fn20dzddz char(60) not null ,
    fn20cks integer not null ,
    fn20dbs integer not null ,
    fn20mysr decimal(16,2) not null ,
    fn20hyzk char(16) not null ,
    fn20gyrk integer not null ,
    fn20whcd integer not null ,
    fn20xydj integer not null ,
    fn20fzdq char(4) not null ,
    fn20sflb char(1) not null ,
    fn20zgxw char(1) not null ,
    fn20zc char(1) not null ,
    fn20by1 integer not null ,
    fn20by2 integer not null ,
    fn20by3 char(15) not null ,
    fn20by4 decimal(16,2) not null 
  );
create unique index sn20_idx_0 on grInfo (fn20khbh)   using btree ;
create index sn20_idx_1 on grInfo (fn20zjbh)   using btree ;
create index sn20_idx_2 on grInfo (fn20xm)   using btree ;


--对公客户信息
create table dgInfo
  (
    fs14khbh char(20) not null ,
    fs14khmc char(60) not null ,
    fs14dwjc char(15) not null ,
    fs14ywmc char(30) not null ,
    fs14zjlx char(1) not null ,
    fs14zjhm char(30) not null ,
    fs14fax char(20) not null ,
    fs14db char(20) not null ,
    fs14tel char(20) not null ,
    fs14swbh char(22) not null ,
    fs14zcd char(3) not null ,
    fs14yxq date not null ,
    fs14jyfw char(30) not null ,
    fs14email char(40) not null ,
    fs14zy char(30) not null ,
    fs14dkzh char(19) not null ,
    fs14jbkhh char(40) not null ,
    fs14jbzh char(30) not null ,
    fs14xydj integer not null ,
    fs14khdz char(60) not null ,
    fs14dqbh integer not null ,
    fs14zgbm integer not null ,
    fs14dwxz integer not null ,
    fs14dwjb integer not null ,
    fs14yzbm integer not null ,
    fs14frdb char(20) not null ,
    fs14zjl char(20) not null ,
    fs14cwfzr char(20) not null ,
    fs14lxr char(20) not null ,
    fs14lxrxm char(60) not null ,
    fs14lxdh char(20) not null ,
    fs14zgr char(30) not null ,
    fs14zcrq date not null ,
    fs14zchm char(20) not null ,
    fs14khh integer not null ,
    fs14jjxz integer not null ,
    fs14zzfs integer not null ,
    fs14zzje decimal(16,2) not null ,
    fs14qxje decimal(16,2) not null ,
    fs14bz char(60) not null ,
    fs14zczj decimal(16,2) not null ,
    fs14hylx integer not null ,
    fs14jgdm char(20) not null ,
    fs14fzrbz char(1) not null ,
    fs14fzrxm char(24) not null ,
    fs14fzrzj char(1) not null ,
    fs14fzrzjhh char(20) not null ,
    fs14dsbh char(22) not null ,
    fs14zjbz char(1) not null ,
    fs14gb char(3) not null ,
    fs14clnf char(4) not null ,
    fs14zclx char(3) not null ,
    fs14cyrs integer not null ,
    fs14qytz char(1) not null ,
    fs14cpqk char(100) not null ,
    fs14cdmj decimal(16,2) not null ,
    fs14czbz char(1) not null ,
    fs14jtbz char(1) not null ,
    fs14jkqbz char(1) not null ,
    fs14ssbz char(1) not null ,
    fs14gpdm char(10) not null ,
    fs14ssd char(2) not null ,
    fs14by1 integer not null ,
    fs14by2 integer not null ,
    fs14by3 char(15) not null ,
    fs14by4 decimal(16,2) not null 
  );
create unique index ss14_idx_0 on dgInfo (fs14khbh)    using btree ;
create index ss14_idx_1 on dgInfo(fs14khmc,fs14khdz)    using btree ;

--个人账户动态
create table grAcct
  (
    fb3hh integer not null ,
    fb3ywlx integer not null ,
    fb3biz integer not null ,
    fb3zh char(30) not null ,
    fb3kmh integer not null ,
    fb3zl integer not null ,
    fb3khbh char(20) not null ,
    fb3ye decimal(16,2) not null ,
    fb3zye decimal(16,2) not null ,
    fb3js decimal(16,2) not null ,
    fb3rs integer not null ,
    fb3yexz decimal(16,2) not null ,
    fb3kzqlj decimal(16,2) not null ,
    fb3zqlj decimal(16,2) not null ,
    fb3drzqlj decimal(16,2) not null ,
    fb3zqcs integer not null ,
    fb3zqzqe decimal(16,2) not null ,
    fb3zqzq integer not null ,
    fb3zqr date not null ,
    fb3jxfszh integer not null ,
    fb3khr date not null ,
    fb3qxr date not null ,
    fb3zhfsr date not null ,
    fb3zhdhr date not null ,
    fb3dqr date not null ,
    fb3xhr date not null ,
    fb3bz char(16) not null ,
    fb3lxs char(1) not null ,
    fb3jxbz integer not null ,
    fb3zcfzr integer not null ,
    fb3jsbz char(1) not null ,
    fb3bs integer not null ,
    fb3wdbs integer not null ,
    fb3yc integer not null ,
    fb3zcbz integer not null ,
    fb3dac char(16)  default '*' not null ,
    fb3by1 integer not null ,
    fb3by2 integer not null ,
    fb3by3 char(15) not null ,
    fb3by4 decimal(16,2) not null 
  );
create unique index sb3_idx_0 on grAcct(fb3zh)   using btree ;
create index sb3_idx_1 on grAcct (fb3hh,fb3zh)   using btree ;
create index sb3_idx_2 on grAcct (fb3jxfszh)   using btree ;
create index sb3_idx_3 on  grAcct (fb3hh,fb3ywlx,fb3biz,fb3kmh)   using btree ;
create index sb3_idx_4 on  grAcct (fb3khbh)   using btree ;
  
--个人账户交易明细
create table grAcctDetail 
  (
    fb311xh integer not null ,
    fb311hh integer not null ,
    fb311jyjqh integer not null ,
    fb311ywlx integer not null ,
    fb311biz integer not null ,
    fb311zh char(30) not null ,
    fb311khzh char(30) not null ,
    fb311kh char(30) not null ,
    fb311jzr date not null ,
    fb311jym char(6) not null ,
    fb311ytm integer not null ,
    fb311jybz char(40) not null ,
    fb311pzl integer not null ,
    fb311pzh char(30) not null ,
    fb311czr date not null ,
    fb311jdf integer not null ,
    fb311fse decimal(16,2) not null ,
    fb311ye decimal(16,2) not null ,
    fb311rs integer not null ,
    fb311js decimal(16,2) not null ,
    fb311fsfhh integer not null ,
    fb311jyhh integer not null ,
    fb311shbh char(15) not null ,
    fb311sqh integer not null ,
    fb311jylsh integer not null ,
    fb311dfhh integer not null ,
    fb311dfzh char(30) not null ,
    fb311jzy integer not null ,
    fb311fhy integer not null ,
    fb311bz char(8) not null ,
    fb311jysj integer not null ,
    fb311jyw integer not null ,
    fb311qtjym char(6) not null ,
    fb311by1 integer not null ,
    fb311by2 integer not null ,
    fb311by3 char(15) not null ,
    fb311by4 decimal(16,2) not null ,
    fb311dfmc char(80),
    fbb311dfhm char(80)
  );

create index sb311_idx_0 on grAcctDetail (fb311kh)   using btree ;
create index sb311_idx_1 on grAcctDetail (fb311hh)   using btree ;
create unique index sb311_idx_2 on grAcctDetail (fb311zh,fb311xh)   using btree ;
create index sb311_idx_3 on grAcctDetail (fb311khzh,fb311jzr)   using btree ;
  
--对公账户动态
create table dgAcct
  (
    fb1hh integer not null ,
    fb1ywlx integer not null ,
    fb1biz integer not null ,
    fb1zh char(30) not null ,
    fb1kmh integer not null ,
    fb1zl integer not null ,
    fb1yef integer not null ,
    fb1ye decimal(16,2) not null ,
    fb1zye decimal(16,2) not null ,
    fb1jclj decimal(16,2) not null ,
    fb1zclj decimal(16,2) not null ,
    fb1yc integer not null ,
    fb1yexz decimal(16,2) not null ,
    fb1js decimal(16,2) not null ,
    fb1rs integer not null ,
    fb1zhfsr date not null ,
    fb1zhdhr date not null ,
    fb1jxfszh integer not null ,
    fb1bz char(16) not null ,
    fb1jxbz integer not null ,
    fb1khbh char(20) not null ,
    fb1xmbh integer not null ,
    fb1khr date not null ,
    fb1qxr date not null ,
    fb1dqr date not null ,
    fb1fdll decimal(10,6) not null ,
    fb1fdllbz char(1) not null ,
    fb1zhlx integer not null ,
    fb1xhr date not null ,
    fb1lxs char(1) not null ,
    fb1ckfzr integer not null ,
    fb1drjqlj decimal(16,2) not null ,
    fb1zcbz integer not null ,
    fb1zyzhlx integer not null ,
    fb1qxbz integer not null ,
    fb1bdhclbz integer not null ,
    fb1dac char(16)  default '*' not null ,
    fb1by1 integer not null ,
    fb1by2 integer not null ,
    fb1by3 char(15) not null ,
    fb1by4 decimal(16,2) not null 
  );
create unique index sb1_idx_0 on dgAcct(fb1zh)   using btree ;
create index sb1_idx_1 on dgAcct (fb1hh,fb1ywlx,fb1biz,fb1kmh)   using btree ;
create index sb1_idx_2 on dgAcct (fb1jxfszh)  using btree;

--对公账户交易明细信息
create table dgAcctDetail 
  (
    fbb111xh integer not null ,
    fbb111hh integer not null ,
    fbb111jyjqh integer not null ,
    fbb111ywlx integer not null ,
    fbb111biz integer not null ,
    fbb111zh char(30) not null ,
    fbb111khzh char(30) not null ,
    fbb111jzr date not null ,
    fbb111jym char(6) not null ,
    fbb111pzl integer not null ,
    fbb111ytm integer not null ,
    fbb111jybz char(40) not null ,
    fbb111pzh char(30) not null ,
    fbb111czr date not null ,
    fbb111jdf integer not null ,
    fbb111fse decimal(16,2) not null ,
    fbb111yef integer not null ,
    fbb111ye decimal(16,2) not null ,
    fbb111rs integer not null ,
    fbb111js decimal(16,2) not null ,
    fbb111sqh integer not null ,
    fbb111jylsh integer not null ,
    fbb111fsfhh integer not null ,
    fbb111jyhh integer not null ,
    fbb111shbh char(15) not null ,
    fbb111dfhh integer not null ,
    fbb111dfzh char(30) not null ,
    fbb111jzy integer not null ,
    fbb111fhy integer not null ,
    fbb111bz char(8) not null ,
    fbb111jysj integer not null ,
    fbb111jyw integer not null ,
    fbb111qtjym char(6) not null ,
    fbb111by1 integer not null ,
    fbb111by2 integer not null ,
    fbb111by3 char(15) not null ,
    fbb111by4 decimal(16,2) not null ,
    fbb111dfmc char(80),
    fbbb111dfhm char(80)
  );
create index i_sbb111_2 on dgAcctDetail(fbb111khzh,fbb111jzr)   using btree ;
create unique index sbb111_idx_0 on dgAcctDetail(fbb111zh,fbb111xh)  using btree ;


--内部账号-客户账号对照表
create table relevantAcct 
  (
    fn13nbzh char(30) not null ,
    fn13gzh integer not null ,
    fn13khzh char(30) not null ,
    fn13xh integer not null ,
    fn13bz char(8) not null ,
    fn13by1 integer not null ,
    fn13by2 integer not null ,
    fn13by3 char(15) not null ,
    fn13by4 decimal(16,2) not null 
  );
create unique index sn13_idx_0 on relevantAcct (fn13khzh,fn13xh) using btree ;
create index sn13_idx_1 on relevantAcct (fn13nbzh,fn13bz) using btree ;
create index sn13_idx_2 on relevantAcct (fn13khzh,fn13bz) using btree ;
