# 薪资管理系统
## 一、系统介绍
这是一个界面简洁的薪资管理系统，虽然界面简单，但其工资项配置，与考勤挂钩，自动核算功能强大，并有完整的从菜单到按钮的操作权限控制。

系统分为了五大模块：考勤与工资，薪资设置，部门管理，公告管理，系统管理。

系统默认有两个角色：管理员，普通用户
- 管理员（admin/admin）：可查看和操作所有菜单
- 普通用户(zhangsan/zhangsan):只可进行考勤，请假，工资查看及工资查询
以上角色均可由管理员自行增设及配置。
  
## 二、角色运行图
### 管理员
![管理员](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/r-%E7%AE%A1%E7%90%86%E5%91%98.png)
### 普通用户
![普通用户](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/r-%E6%99%AE%E9%80%9A%E7%94%A8%E6%88%B7.png)

## 三、所有功能说明
### 0.登录
- 登录地址：http://localhost:8886
- 账号密码：admin/admin

![登录](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/0-1-%E7%99%BB%E5%BD%95.png)
![首页](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/0-2-%E9%A6%96%E9%A1%B5.png)

### 1.考勤与工资
主要包含3个模块，考勤管理，请假管理，工资查询
#### （1）考勤管理
员工可进行上下班打卡，并查看自己考勤记录
![考勤管理-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/1-%E8%80%83%E5%8B%A4%E4%B8%8E%E5%B7%A5%E8%B5%84-%E8%80%83%E5%8B%A4%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.png)
![考勤管理-打卡](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/1-%E8%80%83%E5%8B%A4%E4%B8%8E%E5%B7%A5%E8%B5%84-%E8%80%83%E5%8B%A4%E7%AE%A1%E7%90%86-%E6%89%93%E5%8D%A1.png)

#### （2）请假管理
员工可进行请假申请，领导可进行审批，具有权限控制
![请假管理-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/1-%E8%80%83%E5%8B%A4%E4%B8%8E%E5%B7%A5%E8%B5%84-%E8%AF%B7%E5%81%87%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.png)
![请假管理-请假](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/1-%E8%80%83%E5%8B%A4%E4%B8%8E%E5%B7%A5%E8%B5%84-%E8%AF%B7%E5%81%87%E7%AE%A1%E7%90%86-%E8%AF%B7%E5%81%87.png)
![请假管理-审批](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/1-%E8%80%83%E5%8B%A4%E4%B8%8E%E5%B7%A5%E8%B5%84-%E8%AF%B7%E5%81%87%E7%AE%A1%E7%90%86-%E5%AE%A1%E6%89%B9.png)

#### （3）工资查询
员工可查看自己的工资明细及历史工资记录，并可将工资明细导出excel,领导角色用户可查看其他人工资明细
![工资查询-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/1-%E8%80%83%E5%8B%A4%E4%B8%8E%E5%B7%A5%E8%B5%84-%E5%B7%A5%E8%B5%84%E6%9F%A5%E8%AF%A2-%E5%88%97%E8%A1%A8.png)
![工资查询-明细](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/1-%E8%80%83%E5%8B%A4%E4%B8%8E%E5%B7%A5%E8%B5%84-%E5%B7%A5%E8%B5%84%E6%9F%A5%E8%AF%A2-%E6%98%8E%E7%BB%86.png)
![工资查询-导出](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/1-%E8%80%83%E5%8B%A4%E4%B8%8E%E5%B7%A5%E8%B5%84-%E5%B7%A5%E8%B5%84%E6%9F%A5%E8%AF%A2-%E5%AF%BC%E5%87%BA.png)

### 2.薪资设置
主要有3个模块，薪资配置，薪资核算，和员工薪资配置
#### （1）薪资配置
对公司所有员工的基本工资项进行配置，比如工资的扣除项，五险（养老保险，医疗保险，工伤保险，事业保险，生育保险）个税，公积金，惩罚项：迟到，早退，请假，绩效不达标等。并且配置每项在工资核算时的核算金额。

![薪资配置-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/2-%E8%96%AA%E8%B5%84%E8%AE%BE%E7%BD%AE-%E8%96%AA%E8%B5%84%E9%85%8D%E7%BD%AE-%E5%88%97%E8%A1%A8.png)
![薪资配置-增加](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/2-%E8%96%AA%E8%B5%84%E8%AE%BE%E7%BD%AE-%E8%96%AA%E8%B5%84%E9%85%8D%E7%BD%AE-%E5%A2%9E%E5%8A%A0.png)

#### （2）薪资核算
薪资核算功能有启动核算，核算及查看，删除。
- 启动核算：是用来给财务人员在每月核算工资用的，作用是把当月需要核算工资的员工都列出来，因为每个月可能有员工离职或新员工入职，所以再核算工资前需要先核对有工资的人员列表。
- 核算：选定员工，就可以按照考勤，请假等自动核算出员工该月份的工资，并生成明细。

![薪资核算-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/2-%E8%96%AA%E8%B5%84%E8%AE%BE%E7%BD%AE-%E8%96%AA%E8%B5%84%E6%A0%B8%E7%AE%97-%E5%88%97%E8%A1%A8.png)
![薪资核算-启动核算](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/2-%E8%96%AA%E8%B5%84%E8%AE%BE%E7%BD%AE-%E8%96%AA%E8%B5%84%E6%A0%B8%E7%AE%97-%E5%90%AF%E5%8A%A8%E6%A0%B8%E7%AE%97.png)
![薪资核算-核算](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/2-%E8%96%AA%E8%B5%84%E8%AE%BE%E7%BD%AE-%E8%96%AA%E8%B5%84%E6%A0%B8%E7%AE%97-%E6%A0%B8%E7%AE%97.png)

#### （3）员工薪资配置
主要有功能有配置和批量配置，修改，查看和删除员工配置。
- 配置：指可以为每个员工的每个配置项配置计算系数。比如经理级别的奖金系数就比普通员工要高，这样在计算的时候会把这个系数算进去。
- 批量配置：指可以为每个员工批量做个标准配置，然后那个员工那个配置项需要单独配置的再修改，减轻财务人员工作负担。

![员工薪资配置-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/2-%E8%96%AA%E8%B5%84%E8%AE%BE%E7%BD%AE-%E5%91%98%E5%B7%A5%E8%96%AA%E8%B5%84%E9%85%8D%E7%BD%AE-%E5%88%97%E8%A1%A8.png)
![员工薪资配置-单项配置](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/2-%E8%96%AA%E8%B5%84%E8%AE%BE%E7%BD%AE-%E5%91%98%E5%B7%A5%E8%96%AA%E8%B5%84%E9%85%8D%E7%BD%AE-%E5%8D%95%E9%A1%B9%E9%85%8D%E7%BD%AE.png)

### 3.部门管理
对公司的部门进行增删改查的管理
![部门管理-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/3-%E9%83%A8%E9%97%A8%E7%AE%A1%E7%90%86-%E9%83%A8%E9%97%A8%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.png)
![部门管理-增加](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/3-%E9%83%A8%E9%97%A8%E7%AE%A1%E7%90%86-%E9%83%A8%E9%97%A8%E7%AE%A1%E7%90%86-%E5%A2%9E%E5%8A%A0.png)

### 4.公告管理
公司的一些重大活动进行公示
![公告列表-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/4-%E5%85%AC%E5%91%8A%E7%AE%A1%E7%90%86-%E5%85%AC%E5%91%8A%E5%88%97%E8%A1%A8-%E5%88%97%E8%A1%A8.png)
![公告列表-增加](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/4-%E5%85%AC%E5%91%8A%E7%AE%A1%E7%90%86-%E5%85%AC%E5%91%8A%E5%88%97%E8%A1%A8-%E5%A2%9E%E5%8A%A0.png)

### 5.系统管理
系统管理主要模块有，员工管理，菜单管理，角色管理和字典管理。
#### （1）员工管理
可为新员工增加系统登录账号，为离职员工删除账号。

![员工管理-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E5%91%98%E5%B7%A5%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.png)
![员工管理-增加](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E5%91%98%E5%B7%A5%E7%AE%A1%E7%90%86-%E5%A2%9E%E5%8A%A0.png)

#### （2）菜单管理
管理系统左侧的菜单树，只有管理员可用
![菜单管理-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%8F%9C%E5%8D%95%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.png)
![菜单管理-增加](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%8F%9C%E5%8D%95%E7%AE%A1%E7%90%86-%E5%A2%9E%E5%8A%A0.png)

#### （3）角色管理
配置新角色，为每个角色配置权限，包括菜单权限，数据权限，只有管理员可用
![角色管理-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.png)
![角色管理-增加](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%86-%E5%A2%9E%E5%8A%A0.png)
![角色管理-配置权限](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%86-%E9%85%8D%E7%BD%AE%E6%9D%83%E9%99%90.png)

#### （4）字典管理
管理系统常用字典值，只有管理员可用
![字典管理-列表](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E5%AD%97%E5%85%B8%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.png)
![字典管理-增加](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E5%AD%97%E5%85%B8%E7%AE%A1%E7%90%86-%E5%A2%9E%E5%8A%A0.png)

## 四、软件架构
基础环境：
1. JDK:1.8
2. MySQL:5.7
3. Maven3.0

使用框架：

1. 核心框架：Spring Boot 2.1.8.RELEASE
2. 视图框架：Spring MVC 5.0
3. ORM框架：MyBatisPlus 3.1.2
4. 数据库连接池：Druid 1.1
5. 安全框架：Apache Shiro 1.4
6. 日志：SLF4J 1.7、Log4j
7. 前端框架：jQury,Layui,ztree

## 五、安装教程
1. 导入mysql脚本,数据库名称：salary
2. 修改数据库配置：
![修改数据](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/0-99-%E6%95%B0%E6%8D%AE%E5%BA%93%E9%85%8D%E7%BD%AE.png)
3. 启动java工程（执行salary-web工程com.salary.Application.class中main方法）
![启动项目](https://raw.githubusercontent.com/doudoutangs/salary_system/main/%E8%96%AA%E8%B5%84%E7%AE%A1%E7%90%86/0-99-%E5%90%AF%E5%8A%A8.png)
4. 访问：http://localhost:8886（账号admin/admin）


## 六、合作说明
1. 合作唯一渠道：QQ:553039957详谈。本项目只在gitee和github,gitcode这3个平台上发布，其余平台均为盗版。
2. gitcode主页： https://gitcode.net/tbb414 (推荐)
3. github主页：https://github.com/doudoutangs
4. gitee(码云)主页：https://gitee.com/doudoutang
## 七、其他项目
1. [人事管理系统](https://gitcode.net/tbb414//person_system)
2. [薪资管理系统](https://gitcode.net/tbb414//salary_system)
3. [OA系统](https://gitcode.net/tbb414//oa_system)
4. [招投标管理系统](https://gitcode.net/tbb414//bid_system)
5. [绩效考核系统](https://gitcode.net/tbb414//assess_system)
6. [就业管理系统](https://gitcode.net/tbb414/eta_system)
