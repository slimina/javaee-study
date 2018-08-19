package cn.slimsmart.disruptor.demo.test.demo4;

//事件(Event)就是通过 Disruptor 进行交换的数据类型。
public class LogEvent {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
