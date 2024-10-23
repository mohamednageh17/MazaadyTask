
import com.example.data.remote.api.MazaaddyApiService
import com.example.data.repo.MazaadyRepositoryImpl
import com.example.domain.remote.response.BaseResponse
import com.example.domain.remote.response.category.Categories
import com.example.domain.remote.response.category.Data
import com.example.domain.remote.response.property.Property
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import retrofit2.Response
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MazaadyRepositoryImplTest {

    private lateinit var repository: MazaadyRepositoryImpl
    private lateinit var apiService: MazaaddyApiService

    @Before
    fun setUp() {
        apiService = mockk()

        repository = MazaadyRepositoryImpl(apiService)
    }

    @Test
    fun `getAllCategories should return categories list`() = runTest {
        val mockCategories = arrayListOf(Categories(id = 1, name = "Category 1"))

        val mockResponse: Response<BaseResponse<Data>> = mockk {
            coEvery { isSuccessful } returns true
            coEvery { body() } returns BaseResponse<Data>(data = Data(categories = mockCategories))
        }

        coEvery { apiService.getAllCategories() } returns mockResponse

        val result = repository.getAllCategories().first()

        assertThat(result).isEqualTo(mockCategories)
    }

    @Test
    fun `getProperties should return properties list`() = runTest {

        val mockProperties = listOf(Property(id = 1, name = "Property 1"))

        val mockResponse: Response<BaseResponse<List<Property>>> = mockk {
            coEvery { isSuccessful } returns true
            coEvery { body() } returns BaseResponse<List<Property>>(data =  mockProperties)
        }

        coEvery { apiService.getProperties(cat = 1) } returns mockResponse

        val result = repository.getProperties(1).first()

        assertThat(result).isEqualTo(mockProperties)
    }

    @Test
    fun `getOptionsChild should return child properties list`() = runTest {

        val mockChildProperties = listOf(Property(id = 1, name = "Child Property 1"))

        val mockResponse: Response<BaseResponse<List<Property>>> = mockk {
            coEvery { isSuccessful } returns true
            coEvery { body() } returns BaseResponse<List<Property>>(data = mockChildProperties)
        }

        coEvery { apiService.getOptionsChild(id = 1) } returns mockResponse

        val result = repository.getOptionsChild(1).first()

        assertThat(result).isEqualTo(mockChildProperties)
    }
}
