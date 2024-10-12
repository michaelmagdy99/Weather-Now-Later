package com.example.data.location.repository

import com.example.domain.location.model.LocationDomainModel
import com.example.domain.location.repository.ILocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ILocationRepository {

    override suspend fun getCurrentLocation(): LocationDomainModel {
        val location = fusedLocationProviderClient.lastLocation.await()
        return if (location != null) {
            LocationDomainModel(
                latitude = location.latitude,
                longitude = location.longitude
            )
        } else {
            throw Exception("Unable to retrieve location")
        }
    }
}