package com.afzaln.kotlinnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.afzaln.kotlinnews.data.RedditRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class PostListViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var mockRepository: RedditRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `loading state when fetching posts`() {
        coEvery {
            mockRepository.fetchPosts(any())
        } coAnswers {
            delay(2000)
            mockk(relaxed = true)
        }

        val viewModel = PostListViewModel(mockRepository)
        viewModel.fetchPosts()

        assertEquals(PostListUiState.Loading, viewModel.postsLiveData.value)
    }

    @Test
    fun `loaded state after posts are fetched`() {
        coEvery {
            mockRepository.fetchPosts(any())
        } returns mockk(relaxed = true)

        val viewModel = PostListViewModel(mockRepository)
        viewModel.fetchPosts()

        runBlocking {
            delay(500)
        }

        assertTrue(viewModel.postsLiveData.value is PostListUiState.Loaded)
    }

    @Test
    fun `error state if posts are not fetched`() {
        coEvery {
            mockRepository.fetchPosts(any())
        } coAnswers {
            throw IOException()
        }

        val viewModel = PostListViewModel(mockRepository)
        viewModel.fetchPosts()

        runBlocking {
            delay(500)
        }

        assertEquals(PostListUiState.Error, viewModel.postsLiveData.value)

    }

    @Test
    fun `no network call if posts already loaded`() {
        coEvery {
            mockRepository.fetchPosts(any())
        } returns mockk(relaxed = true)

        val viewModel = PostListViewModel(mockRepository)
        viewModel.fetchPosts()

        runBlocking {
            delay(500)
        }

        assertTrue(viewModel.postsLiveData.value is PostListUiState.Loaded)

        viewModel.fetchPosts()
        coVerify(atMost = 1) {
            mockRepository.fetchPosts(any())
        }
    }
}
