package com.example.fooddeliveryapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.di.viewModel.ViewModelKey
import com.example.fooddeliveryapp.ui.authentication.LogInViewModel
import com.example.fooddeliveryapp.ui.authentication.SingUpViewModel
import com.example.fooddeliveryapp.ui.cart.CartViewModel
import com.example.fooddeliveryapp.ui.cart.purchase.ConfirmPurchaseViewModel
import com.example.fooddeliveryapp.ui.home.HomeViewModel
import com.example.fooddeliveryapp.ui.home.open_product.ProductDescriptionViewModel
import com.example.fooddeliveryapp.ui.home.search.SearchViewModel
import com.example.fooddeliveryapp.ui.profile.ProfileViewModel
import com.example.fooddeliveryapp.ui.profile.user_addresses.UserAddressesViewModel
import com.example.fooddeliveryapp.ui.profile.user_history_order.UserOrderHistoryViewModel
import com.example.fooddeliveryapp.ui.profile.user_history_order.more_detailed_order.MoreDetailedOrderViewModel
import com.example.fooddeliveryapp.ui.profile.user_profile.UserProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    fun bindLoginViewModel(viewModel: LogInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SingUpViewModel::class)
    fun bindSingUpViewModel(viewModel: SingUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmPurchaseViewModel::class)
    fun bindConfirmPurchaseViewModel(viewModel: ConfirmPurchaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    fun bindCartViewModelRefactor(viewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDescriptionViewModel::class)
    fun bindProductDescriptionViewModel(viewModel: ProductDescriptionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserAddressesViewModel::class)
    fun bindUserAddressesViewModel(viewModel: UserAddressesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreDetailedOrderViewModel::class)
    fun bindMoreDetailedOrderViewModel(viewModel: MoreDetailedOrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserOrderHistoryViewModel::class)
    fun bindUserOrderHistoryViewModel(viewModel: UserOrderHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    fun bindUserProfileViewModel(viewModel: UserProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bind(viewModel: ProfileViewModel): ViewModel
}
