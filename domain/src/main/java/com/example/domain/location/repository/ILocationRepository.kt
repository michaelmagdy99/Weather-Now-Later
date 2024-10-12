package com.example.domain.location.repository

import com.example.domain.location.model.LocationDomainModel

interface ILocationRepository {
    suspend fun getCurrentLocation(): LocationDomainModel
}