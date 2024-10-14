package com.example.domain.location.usecase

import com.example.domain.location.model.LocationDomainModel
import com.example.domain.location.repository.ILocationRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class GetLocationUseCaseTest {

    @Mock
    private lateinit var locationRepository: ILocationRepository

    private lateinit var getLocationUseCase: GetLocationUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getLocationUseCase = GetLocationUseCase(locationRepository)
    }

    @Test
    fun `invoke returns LocationDomainModel when successful`() = runBlocking {
        // Given
        val expectedLocationDomainModel = LocationDomainModel(
            latitude = 12.34,
            longitude = 56.78
        )

        // When
        whenever(locationRepository.getCurrentLocation()).thenReturn(expectedLocationDomainModel)

        // Then
        val result = getLocationUseCase()
        assertEquals(expectedLocationDomainModel, result)
    }
}
