namespace java cn.slimsmart.thrift.demo.pool
struct UserRequest
{
	1:string id
}
struct UserResponse
{
	1:string code
	2:map<string,String> params
}
service UserService
{
	UserResponse userInfo(1:UserRequest request)
}