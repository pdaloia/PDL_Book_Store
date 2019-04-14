package rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
public class ItemListWrapper {
	
	private List<ItemWrapper> itemList;
	
	public ItemListWrapper() {
		
	}
	
	public ItemListWrapper(List<ItemWrapper> itemList) {
		super();
		this.itemList = itemList;
	}

	public List<ItemWrapper> getItems() {
		return itemList;
	}

	public void setItems(List<ItemWrapper> itemList) {
		this.itemList = itemList;
	}
	
	
}
