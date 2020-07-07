package com.swj.util;

public enum LogOperateTypeEnum {

    ADD("添加",1),

    DEL("删除",2),

    UPDATE("修改",3),

    LOGIN("登陆",4);
    private String operateDesc;
    private int operateCode;


    LogOperateTypeEnum(String operateDesc,int operateCode){
        this.operateDesc=operateDesc;
        this.operateCode=operateCode;
    }

    public static String getMessage(int operateCode){
        //通过enum.values()获取所有的枚举值
        for(LogOperateTypeEnum logOperateTypeEnum : LogOperateTypeEnum.values()){
            //通过enum.get获取字段值
            if(logOperateTypeEnum.getOperateCode() == operateCode){
                return logOperateTypeEnum.operateDesc;
            }
        }
        return null;
    }

    public String getOperateDesc() {
        return operateDesc;
    }

    public int getOperateCode() {
        return operateCode;
    }

    public void setOperateDesc(String operateDesc) {
        this.operateDesc = operateDesc;
    }

    public void setOperateCode(int operateCode) {
        this.operateCode = operateCode;
    }

}
