package model;

public class TestCase {
    private String element_info; ////*[@id="new_user"]/div[4]/input
    private String find_type;// id,xpath,name,classname
    private String operate_type; // click,send_keys...
    private String text; //文本框内容,或者其他内容
    private String frame; // frame的值
    private String defaultContent; //切换到默认主页面
    public void setElement_info(String element_info) {
        this.element_info = element_info;
    }

    public void setFind_type(String find_type) {
        this.find_type = find_type;
    }

    public void setOperate_type(String operate_type) {
        this.operate_type = operate_type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getElement_info() {

        return element_info;
    }

    public String getFind_type() {
        return find_type;
    }

    public String getOperate_type() {
        return operate_type;
    }

    public String getText() {
        return text;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getDefaultContent() {
        return defaultContent;
    }

    public void setDefaultContent(String defaultContent) {
        this.defaultContent = defaultContent;
    }
}
