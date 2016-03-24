# 礼金理接口文档 #

## 测试ip+端口： 120.25.194.214:8080 ##

### 1. 用户中心保存接口 ###
**接口说明：** 保存用户信息，用户不存在则新增，用户存在则更新

**请求地址：** http://ip/lijinli/user/saveUser.json?data={data}

**参数说明：**(空的就不用传)
<pre>
data(String): json字符串，格式如下：
{
	"userUid":"123",
	"userName":"张三",
	"userSex":"1",
	"userPhone":"110",
	"userLocation":"成都",
	"userPwd":"123"
}
</pre>
**返回结果：**
<pre>
{
    "code": "000000",
    "msg": "操作成功"
}
</pre>

### 2. 用户中心检测用户接口 ###
**接口说明：** 通过手机号或者密码检测用户是否存在

**请求地址：** http://ip/lijinli/user/checkUser.json?data={data}

**参数说明：**(空的就不用传)
<pre>
data(String): json字符串，格式如下：
{
	"phone":"110",
	"pwd":"123"
}
</pre>
**返回结果：**
<pre>
{
    "code": "000001",
    "msg": "用户不存在"
}
</pre>

### 3. 用户中心发送手机验证码接口 ###
**接口说明：** 发送手机验证码到手机号码

**请求地址：** http://ip/lijinli/user/sendPhoneCode.json?phone={phone}

**参数说明：**(空的就不用传)
<pre>
phone(String): 字符串，格式如下：
"123"
</pre>
**返回结果：**
<pre>
手机短信验证码
</pre>

### 4. 用户中心检测手机验证码是否正确接口 ###
**接口说明：** 验证手机验证码

**请求地址：** http://ip/lijinli/user/checkPhoneCode.json?code={code}

**参数说明：**(空的就不用传)
<pre>
code(String): 字符串，格式如下：
"123"
</pre>
**返回结果：**
<pre>
{
    "code": "000001",
    "msg": "验证码错误"
}
</pre>


