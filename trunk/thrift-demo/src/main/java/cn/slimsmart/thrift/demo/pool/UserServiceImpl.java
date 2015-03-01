package cn.slimsmart.thrift.demo.pool;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 *  业务接口实现
 */
@Service
public class UserServiceImpl implements UserService.Iface{

	@Override
	public UserResponse userInfo(UserRequest request) throws TException {
		try{
			UserResponse urp=new UserResponse();
			if(request.id.equals("10000")){
				urp.setCode("0");
				Map<String,String> params= new HashMap<String,String>();
				params.put("name", "lucy");
				urp.setParams(params);
			}
			System.out.println("接收参数是：id="+request.id);
			return urp;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
