package com.huanchengfly.tieba.api.retrofit.interfaces

import com.huanchengfly.tieba.api.getScreenHeight
import com.huanchengfly.tieba.api.getScreenWidth
import com.huanchengfly.tieba.api.models.CommonResponse
import com.huanchengfly.tieba.api.models.ThreadContentBean
import com.huanchengfly.tieba.api.Header
import com.huanchengfly.tieba.post.base.BaseApplication
import com.huanchengfly.tieba.post.base.Config
import com.huanchengfly.tieba.post.utils.AccountUtil
import io.michaelrocks.paranoid.Obfuscate
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

@Obfuscate
interface OfficialTiebaApi {
    @POST("/c/f/pb/page")
    @FormUrlEncoded
    fun threadContent(
            @Field("kz") threadId: String,
            @Field("pn") page: Int,
            @Field("last") last: String?,
            @Field("r") r: String?,
            @Field("lz") lz: Int,
            @Field("st_type") st_type: String = "tb_frslist",
            @Field("back") back: String = "0",
            @Field("floor_rn") floor_rn: String = "3",
            @Field("mark") mark: String = "0",
            @Field("rn") rn: String = "30",
            @Field("with_floor") with_floor: String = "1",
            @Field("scr_dip") scr_dip: String = Config.DENSITY.toString(),
            @Field("scr_h") scr_h: String = getScreenHeight().toString(),
            @Field("scr_w") scr_w: String = getScreenWidth().toString()
    ): Call<ThreadContentBean>

    @POST("/c/f/pb/page")
    @FormUrlEncoded
    fun threadContent(
            @Field("kz") threadId: String,
            @Field("pid") postId: String?,
            @Field("last") last: String?,
            @Field("r") r: String?,
            @Field("lz") lz: Int,
            @Field("st_type") st_type: String = "tb_frslist",
            @Field("back") back: String = "0",
            @Field("floor_rn") floor_rn: String = "3",
            @Field("mark") mark: String = "0",
            @Field("rn") rn: String = "30",
            @Field("with_floor") with_floor: String = "1",
            @Field("scr_dip") scr_dip: String = Config.DENSITY.toString(),
            @Field("scr_h") scr_h: String = getScreenHeight().toString(),
            @Field("scr_w") scr_w: String = getScreenWidth().toString()
    ): Call<ThreadContentBean>

    @Headers("${Header.FORCE_LOGIN}: ${Header.FORCE_LOGIN_TRUE}")
    @POST("/c/c/excellent/submitDislike")
    @FormUrlEncoded
    fun submitDislike(
            @Field("dislike") dislike: String,
            @Field("dislike_from") dislike_from: String = "homepage",
            @Field("stoken") stoken: String = AccountUtil.getSToken(BaseApplication.getInstance())!!
    ): Call<CommonResponse>
}