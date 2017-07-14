package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class ElementLocator {
	private final static String LOCATE_ID = "id";
	private final static String LOCATE_NAME = "name";
	private final static String LOCATE_LINKTEXT = "linkText";
	private final static String LOCATE_PARTIALINKTEXT = "partialLinkText";
	private final static String LOCATE_TAGNAME = "tagName";
	private final static String LOCATE_CLASSNAME = "className";
	private final static String LOCATE_XPATH = "xpath";

	private ElementLocator parent;

	public ElementLocator() {
		parent = null;
	}
	
	// private static class LazyHolder {
		// private static final CommonFunction INSTANCE = new CommonFunction();
		// }
		// private CommonFunction(){}
		// public static final CommonFunction getInstance() {
		// return LazyHolder.INSTANCE;
		// }

	public static ElementLocator create(final String expr) {
		String[] sType = expr.split("::");
		if (sType[0].equalsIgnoreCase(LOCATE_ID)) {
			return id(sType[1]);
		} else if (sType[0].equalsIgnoreCase(LOCATE_NAME)) {
			return name(sType[1]);
		} else if (sType[0].equalsIgnoreCase(LOCATE_LINKTEXT)) {
			return linkText(sType[1]);
		} else if (sType[0].equalsIgnoreCase(LOCATE_PARTIALINKTEXT)) {
			return partialLinkText(sType[1]);
		} else if (sType[0].equalsIgnoreCase(LOCATE_TAGNAME)) {
			return tagName(sType[1]);
		} else if (sType[0].equalsIgnoreCase(LOCATE_CLASSNAME)) {
			return className(sType[1]);
		} else if (sType[0].equalsIgnoreCase(LOCATE_XPATH)) {
			return xPath(sType[1]);
		}else{
			System.out.println("参数不能解析,未知类型:" + expr);
			return null;
		}
	}

	public static ElementLocator id(final String id) {
		return new SeleniumElementLocator(By.id(id));
	}

	public static ElementLocator name(final String name) {
		return new SeleniumElementLocator(By.name(name));
	}

	public static ElementLocator linkText(final String text) {
		return new SeleniumElementLocator(By.linkText(text));
	}

	public static ElementLocator partialLinkText(final String text) {
		return new SeleniumElementLocator(By.partialLinkText(text));
	}

	public static ElementLocator tagName(final String name) {
		return new SeleniumElementLocator(By.tagName(name));
	}

	public static ElementLocator className(final String name) {
		return new SeleniumElementLocator(By.className(name));
	}

	public static ElementLocator xPath(final String expr) {
		return new SeleniumElementLocator(By.xpath(expr));
	}

	public static ElementLocator element(final WebElement element) {
		return new DirectElementLocator(element);
	}

	public abstract List<WebElement> findElements(WebDriver driver);

	public abstract WebElement locate(WebDriver driver);

	public void setParent(ElementLocator parent) {
		this.parent = parent;
	}

	public ElementLocator getParent() {
		return parent;
	}
}

class SeleniumElementLocator extends ElementLocator {
	private By by;

	public SeleniumElementLocator(By by) {
		this.by = by;
	}

	public List<WebElement> findElements(WebDriver driver) {
		SearchContext parentElement = driver;
		if (getParent() != null) {
			parentElement = getParent().locate(driver);
		}

		return parentElement.findElements(by);
	}

	public WebElement locate(WebDriver driver) {
		SearchContext parentElement = driver;
		if (getParent() != null) {
			parentElement = getParent().locate(driver);
		}

		WebElement element = parentElement.findElement(by);
		return element;
	}
}


class DirectElementLocator extends ElementLocator {
	private ArrayList<WebElement> elementList;

	public DirectElementLocator(WebElement element) {
		elementList = new ArrayList<WebElement>();
		elementList.add(element);
	}

	public List<WebElement> findElements(WebDriver driver) {
		return elementList;
	}

	public WebElement locate(WebDriver driver) {
		return elementList.get(0);
	}
}