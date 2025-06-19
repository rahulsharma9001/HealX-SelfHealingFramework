package SelfHealing;

import org.openqa.selenium.By;

import java.lang.reflect.Field;

public class Details {
    public String getLocatorName(By locator) {
        Class<?> clazz = this.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(this);
                    if (fieldValue != null && fieldValue.equals(locator)) {
                        return field.getName();
                    }
//                    if (field.get(this).equals(locator)) {
//                        return ( field.getName());
//                        // Add more cases if you use other By types
//                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass(); // Move to the superclass to check fields there
        }
        return null;
    }
//    public String getLocatorValue(){
//
//    }
//    public String getLocatorType(){
//
//    }
}
