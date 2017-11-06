package com.example.luzhichao.datepickerproject;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author luzhichao
 * @version 2.3, 2017/11/4
 * @since [DatePicker/V2.3.5]
 */

public class DefaultApplicationLike extends TinkerApplication {
    public DefaultApplicationLike() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.luzhichao.datepickerproject.SampleApplication",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
