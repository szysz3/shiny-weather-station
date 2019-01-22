package com.pinetreeapps.shinystationframe.data.repository

import com.pinetreeapps.shinystationframe.data.repository.local.LocalStorageApi
import com.pinetreeapps.shinystationframe.data.repository.remote.RemoteApi

interface WeatherStationApi : RemoteApi, LocalStorageApi
