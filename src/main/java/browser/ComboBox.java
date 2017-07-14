package browser;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

//组合框  控件是由一个文本输入控件和一个下拉菜单组成的
public class ComboBox extends Select
{
    public ComboBox(WebElement element)
    {
        super(element);
    }
}
