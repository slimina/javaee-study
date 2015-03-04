	今天来学习一下java.lang.reflect包下有关反射的相关内容，提供类和接口，以获得关于类和对象的反射信息。在安全限制内，
反射允许编程访问关于加载类的字段、方法和构造方法的信息，并允许使用反射字段、方法和构造方法对其底层对等项进行操作。
下面主要介绍如下几个类：
1.Class
	万事万物皆对象，平时我们创建的USer、Role类就是Class的实例对象,Class是对类的描述，即类类型。class类的实例表示java应用运行时的类(class 
ans enum)或接口(interface and annotation)。数组同样也被映射为为class 对象的一个类，所有具有相同元素类型和维数
的数组都共享该 Class 对象。基本类型boolean，byte，char，short，int，long，float，double和关键字void同样表现为 
class 对象。
  Class类没有公有的构造方法，它由JVM自动调用，我有如下3种方式获取Class
  （1）某个对象实例的getClass()方法，如new User().getClass()
  （2）某个类名.class属性，如User.class
  （3）通过Class.forName("类名")获取
 我们看一下下面的实例：
 
从上面实例可以看出，我们获取的c1,c2,c3都是同一个class，是对User类的表示。
Class有以下几个类主要方法：
（1）forName 获取给定字符串名的类或接口相关联的 Class 对象
（2）getAnnotation 获取该类的类注解
（3）getClasses 获取该类的父类或接口
（4）获取构造方法
（5）获取成员属性
（6）获取成员方法
（7）newInstance 创建此 Class 对象所表示的类的一个新实例。
方法中带Declared获取该类的所有属性、方法、注解等，不包含继承的，不带Declared的是只包含所有公共的属性、方法、注解等
2.Field
 描述类的成员属性，如user.name属性，Field 提供有关类或接口的单个字段的信息，以及对它的动态访问权限。反射的字段可能是
 一个类（静态）字段或实例字段。
可以通过class的getDeclaredField(String name),getDeclaredFields(),getField(String name),getFields()获取，通过Field的
方法可以获取、设置属性的值，并能获取属性的注解、字段的声明类型。
如下实例：
3.Method
描述类的成员方法，Method 提供关于类或接口上单独某个方法（以及如何访问该方法）的信息。所反映的方法可能是类方法或实例方法
（包括抽象方法）。Method 允许在匹配要调用的实参与底层方法的形参时进行扩展转换
可以通过class的getDeclaredMethod(String name, Class<?>... parameterTypes) ，getDeclaredMethods() ，getMethod(String name,
 Class<?>... parameterTypes)，getMethods() 获取，通过Method的invoke方法去执行，获取返回值，也可以获取方法注解、
 返回值类型等。
如下实例：
4.Constructor
Constructor是对构造方法的声明描述，Constructor 提供关于类的单个构造方法的信息以及对它的访问权限。Constructor 
允许在将实参与带有底层构造方法的形参的 newInstance() 匹配时进行扩展转换。可以通过class的getConstructor(Class<?>... parameterTypes) 
，getConstructors()，getDeclaredConstructor(Class<?>... parameterTypes) ，getDeclaredConstructors() 获取。通过Constructor
可以获取注解，参数类型等，并通过newInstance(Object... initargs) 创建类实例。
如下实例：
5.Array
Array 类提供了动态创建和访问 Java 数组的方法。允许在执行 get 或 set 操作期间进行扩展转换。
可以通过class的isArray方法判定此 Class 对象是否表示一个数组类，getComponentType返回表示数组组件类型的 Class。通过newInstance初始化
数组。
如下实例：


6.Proxy
Proxy 提供用于创建动态代理类和实例的静态方法，它还是由这些方法创建的所有动态代理类的超类。
动态代理类（以下简称为代理类）是一个实现在创建类时在运行时指定的接口列表的类，该类具有下面描述的行为。 代理接口 是代理类实现的一个接口。 代理实例 是代理类的一个实例。 每个代理实例都有一个关联的调用处理程序 对象，它可以实现接口 InvocationHandler。通过其中一个代理接口的代理实例上的方法调用将被指派到实例的调用处理程序的 Invoke 方法，并传递代理实例、识别调用方法的 java.lang.reflect.Method 对象以及包含参数的 Object 类型的数组。调用处理程序以适当的方式处理编码的方法调用，并且它返回的结果将作为代理实例上方法调用的结果返回。
代理类具用以下属性：
(1)代理类是公共的、最终的，而不是抽象的。
(2)未指定代理类的非限定名称。但是，以字符串 "$Proxy" 开头的类名空间应该为代理类保留。
(3)代理类扩展 java.lang.reflect.Proxy。
(4)代理类会按同一顺序准确地实现其创建时指定的接口。
(5)如果代理类实现了非公共接口，那么它将在与该接口相同的包中定义。否则，代理类的包也是未指定的。注意，包密封将不阻止代理类在运行时在特定包中的成功定义，也不会阻止相同类加载器和带有特定签名的包所定义的类。
(6)由于代理类将实现所有在其创建时指定的接口，所以对其 Class 对象调用 getInterfaces 将返回一个包含相同接口列表的数组（按其创建时指定的顺序），对其 Class 对象调用 getMethods 将返回一个包括这些接口中所有方法的 Method 对象的数组，并且调用 getMethod 将会在代理接口中找到期望的一些方法。
(7)如果 Proxy.isProxyClass 方法传递代理类（由 Proxy.getProxyClass 返回的类，或由 Proxy.newProxyInstance 返回的对象的类），则该方法返回 true，否则返回 false。
(8)代理类的 java.security.ProtectionDomain 与由引导类加载器（如 java.lang.Object）加载的系统类相同，原因是代理类的代码由受信任的系统代码生成。此保护域通常被授予 java.security.AllPermission。
(9)每个代理类都有一个可以带一个参数（接口 InvocationHandler 的实现）的公共构造方法，用于设置代理实例的调用处理程序。并非必须使用反射 API 才能访问公共构造方法，通过调用 Proxy.newInstance 方法（将调用 Proxy.getProxyClass 的操作和调用带有调用处理程序的构造方法结合在一起）也可以创建代理实例。
静态方法newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) 返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序。