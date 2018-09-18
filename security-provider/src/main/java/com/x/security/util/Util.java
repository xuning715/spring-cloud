package com.x.security.util;

public class Util {
    private static String CODE_0001 = "0001";
    private static String CODE_0 = "0";

    public static String getCode(String parentCode, String maxCode) {
		if (maxCode == null) {
			return parentCode + CODE_0001;
		}

		// maxCode的下一seq
		int seq = Integer.parseInt(maxCode.substring(parentCode.length())) + 1;

		// 根据顺序算出最终的code
		String seqStr = String.valueOf(seq);
		// 前面补0至长度为4
		int len = seqStr.length();
		for (int i = 0; i < 4 - len; i++) {
			seqStr = CODE_0 + seqStr;
		}
		// 没有父节点，那肯定是第一级的
		maxCode = parentCode + seqStr;

		return maxCode;
	}
}
