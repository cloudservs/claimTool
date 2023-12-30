package com.cloudservs.claimtool.domain.claim;


import java.beans.Transient;

public class Reference {
  String code;
  String _id;
  String name;
  String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String get_id() {
        return _id;
    }
      public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
