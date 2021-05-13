
package com.vaccine.poller
import com.vaccine.poller.models.CentersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface APIInterface {
    @GET("/api/v2/appointment/sessions/calendarByDistrict")
    fun getCenters(
        @Query("district_id") districtId: Int,
        @Query("date") date: String
    ): Call<CentersResponse>

}