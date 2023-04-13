package com.example.fooddeliveryapp.di

import android.content.Context
import com.example.fooddeliveryapp.MainActivity
import com.example.fooddeliveryapp.di.viewModel.ViewModelModule
import com.example.fooddeliveryapp.ui.authentication.LogInFragment
import com.example.fooddeliveryapp.ui.authentication.NeedAuthenticationFragment
import com.example.fooddeliveryapp.ui.authentication.SignUpFragment
import com.example.fooddeliveryapp.ui.authentication.SuccessAuthenticationFragment
import com.example.fooddeliveryapp.ui.cart.CartFragment
import com.example.fooddeliveryapp.ui.cart.purchase.ConfirmPurchaseFragment
import com.example.fooddeliveryapp.ui.cart.purchase.SuccessPurchaseFragment
import com.example.fooddeliveryapp.ui.contacts.ContactsFragment
import com.example.fooddeliveryapp.ui.home.HomeFragment
import com.example.fooddeliveryapp.ui.home.open_product.ProductDescriptionFragment
import com.example.fooddeliveryapp.ui.home.search.SearchFragment
import com.example.fooddeliveryapp.ui.profile.ProfileFragment
import com.example.fooddeliveryapp.ui.profile.user_addresses.UserAddressesFragment
import com.example.fooddeliveryapp.ui.profile.user_history_order.UserOrderHistoryFragment
import com.example.fooddeliveryapp.ui.profile.user_history_order.more_detailed_order.MoreDetailedOrderFragment
import com.example.fooddeliveryapp.ui.profile.user_profile.UserProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, DataBaseModule::class, SourceModule::class, NetworkModule::class, DataModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: LogInFragment)
    fun inject(activity: SignUpFragment)
    fun inject(activity: NeedAuthenticationFragment)
    fun inject(activity: SuccessAuthenticationFragment)
    fun inject(activity: ConfirmPurchaseFragment)
    fun inject(activity: SuccessPurchaseFragment)
    fun inject(activity: ProductDescriptionFragment)
    fun inject(activity: SearchFragment)
    fun inject(activity: HomeFragment)
    fun inject(activity: CartFragment)
    fun inject(activity: UserAddressesFragment)
    fun inject(activity: MoreDetailedOrderFragment)
    fun inject(activity: UserOrderHistoryFragment)
    fun inject(activity: ProfileFragment)
    fun inject(activity: UserProfileFragment)
    fun inject(activity: ContactsFragment)
}