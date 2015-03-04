package cn.slimsmart.protoc.demo;

public class Test {

	public static void main(String[] args) {
		User.UserInfo.Builder builder =User.UserInfo.newBuilder();
		builder.setId("699B599EF7E44EEFA0B9A659A03CD159");
		builder.setAge(55);
		builder.setName("lucy");
		builder.setDesc("hello world");
		
		User.UserInfo userinfo = builder.build();
		System.out.println(userinfo.toString());
		System.out.println(userinfo.toByteString().toStringUtf8());
	}
}
