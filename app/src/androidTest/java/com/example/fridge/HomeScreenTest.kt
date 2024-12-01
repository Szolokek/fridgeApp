package com.example.fridge
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fridge.data.FridgeItem
import com.example.fridge.ui.home.HomeScreen
import com.example.fridge.ui.home.HomeUiState
import com.example.fridge.ui.home.HomeViewModel
import com.example.fridge.ui.item.ItemDetails
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK


@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    private lateinit var viewModel: HomeViewModel // Assuming your ViewModel is named HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        // Mock the data in your ViewModel
        every { viewModel.homeUiState.value.itemList } returns listOf(
            FridgeItem(
                id =0,
                name = "Item 1",
                imageData = null
            ),
            FridgeItem(
                id =1,
                name = "Item 2",
                imageData = null
            )
        )
        // Set the mocked ViewModel in your activity or composable
        composeTestRule.setContent {
            HomeScreen(
                viewModel = viewModel,
                navigateToItemEntry = {},
                navigateToItemUpdate = {},
                modifier = Modifier
            ) // Assuming your composable is named HomeScreen
        }
    }

    @Test
    fun testItemsAreDisplayed() {
        composeTestRule.onNodeWithTag("itemList").assertIsDisplayed()
        // Assuming you have items with specific text or IDs
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 2").assertIsDisplayed()
    }

    @Test
    fun testSearchFunctionality() {
        // Assuming you have a search bar with a specific tag or ID
        composeTestRule.onNodeWithTag("searchBar")
            .performTextInput("Item 1")

        // After search, assert that only "Item 1" is displayed
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 2").assertDoesNotExist()
    }
}