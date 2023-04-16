package com.example.fooddeliveryapp.ui.contacts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentRestaurantsMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MapsRestaurantsFragment : Fragment() {

    private var _binding: FragmentRestaurantsMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private lateinit var mapObjects: MapObjectCollection
    private val locationMinsk = Point(53.9045, 27.5615)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRestaurantsMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMap()
        setPlaceMark()
    }

    private fun setMap() {
        mapView = binding.mapView
        mapObjects = mapView.map.mapObjects
        mapView.map.move(
            CameraPosition(locationMinsk, 12.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )
    }

    private fun setPlaceMark() {
        addPlaceMarkOnMap(Point(53.9045, 27.5615))
        addPlaceMarkOnMap(Point(53.8930, 27.5675))
        addPlaceMarkOnMap(Point(53.9130, 27.5485))
        addPlaceMarkOnMap(Point(53.9175, 27.5805))
    }

    private fun addPlaceMarkOnMap(point: Point) {
        val imageLink = R.drawable.ic_restaurants_placeholder
        val imageBitMap = ContextCompat.getDrawable(requireContext(), imageLink)?.toBitmap()
        val restaurantsHolderImage = ImageProvider.fromBitmap(imageBitMap)
        val iconStyle = IconStyle().setScale(0.2f)
        mapView.map.mapObjects.addPlacemark(point, restaurantsHolderImage, iconStyle)
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}