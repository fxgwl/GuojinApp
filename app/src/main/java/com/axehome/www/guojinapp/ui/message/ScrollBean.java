package com.axehome.www.guojinapp.ui.message;

import com.axehome.www.guojinapp.beans.PreGoodsBean;
import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Raul_lsj on 2018/3/22.
 */

public class ScrollBean extends SectionEntity<ScrollBean.ScrollItemBean> {

    public ScrollBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ScrollBean(ScrollBean.ScrollItemBean bean) {
        super(bean);
    }

    public static class ScrollItemBean {
        private String text;
        private String type;
        private PreGoodsBean preGoodsBean;

        public ScrollItemBean(String text, String type,PreGoodsBean preGoodsBean) {
            this.text = text;
            this.type = type;
            this.preGoodsBean = preGoodsBean;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public PreGoodsBean getPreGoodsBean() {
            return preGoodsBean;
        }

        public void setPreGoodsBean(PreGoodsBean preGoodsBean) {
            this.preGoodsBean = preGoodsBean;
        }
    }
}
