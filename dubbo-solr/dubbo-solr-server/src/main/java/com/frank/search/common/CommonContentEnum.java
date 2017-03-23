package com.frank.search.common;

/**
 * Created with IntelliJ IDEA. User: frank Date: 15-12-1 Time: 下午2:43 自定义枚举类型
 */
public class CommonContentEnum {

	/**
	 * 自定义分类
	 */
	public static enum UserDefineSort {
		ZEROPRODUCT("ZEROPRODUCT"), NEWUSER("NEWUSER"), PROMOTION("Promotion"), HOTPRODUCT("HotProduct");
		private String value;

		private UserDefineSort(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public String toString() {
			return this.value;
		}
	}

	// 销售类型 0：零售，1：线上销售，2：线上销售和零售
	public static enum SellType {
		ZEROSELL("0"), ONESELL("1"), SENOND("2");

		private String value;

		private SellType(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public String toString() {
			return this.value;
		}

	}
}
