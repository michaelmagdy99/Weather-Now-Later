package com.example.domain.location.usecase

import com.example.domain.location.model.LocationDomainModel
import com.example.domain.location.repository.ILocationRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(private val locationRepository: ILocationRepository)
{
    suspend operator fun invoke(): LocationDomainModel {
        return locationRepository.getCurrentLocation()
    }
}