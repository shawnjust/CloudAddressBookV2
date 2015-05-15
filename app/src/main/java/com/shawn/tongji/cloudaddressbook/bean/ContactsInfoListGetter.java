package com.shawn.tongji.cloudaddressbook.bean;

import com.shawn.tongji.cloudaddressbook.annotation.Display;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ContactsInfoListGetter implements Serializable {

    public List<ContactsInfoItem> getContactsInfoItem() {
        List<ContactsInfoItem> list = new ArrayList<ContactsInfoItem>();
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Display.class) && field.get(this) != null && !"".equals(String.valueOf(field.get(this)))) {
                    Display display = field.getAnnotation(Display.class);
                    ContactsInfoItem contactsInfoItem = new ContactsInfoItem();
                    contactsInfoItem.setKey(display.fieldKey());
                    contactsInfoItem.setGravity(display.gravity());
                    contactsInfoItem.setValue(String.valueOf(field.get(this)));
                    contactsInfoItem.setIcon(display.drawableId());
                    list.add(contactsInfoItem);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
