package com.android.personalmushaf.model

data class AyahCoordinates(val page: Int,
                           val ayahCoordinates: Map<String, List<AyahBounds>>)