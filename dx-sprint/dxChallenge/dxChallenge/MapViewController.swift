//
//  MapViewController.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/07.
//

import UIKit
import GoogleMaps
class MapViewController: UIViewController{
    var store: StoreList?
    override func viewDidLoad() {
        super.viewDidLoad()

        let latitude = Double( store?.x ?? "35.1732211")!
        let longitude = Double(store?.y ?? "129.13045092")!
        let camera = GMSCameraPosition.camera(withLatitude:latitude, longitude: longitude, zoom: 16.0)

        let mapView = GMSMapView(frame: .zero, camera: camera)
        self.view = mapView
        let position = CLLocationCoordinate2D(latitude:latitude, longitude:longitude)
        let marker = GMSMarker(position: position)
        marker.title = store?.store_nm
        marker.snippet = store?.address
        marker.map = mapView


//    let camera = GMSCameraPosition.camera(withLatitude: -33.86, longitude: 151.20, zoom: 6.0)
//    let mapView = GMSMapView.map(withFrame: CGRect.zero, camera: camera)
//    view = mapView
//        let marker = GMSMarker()
//        marker.position = CLLocationCoordinate2D(latitude: -33.86, longitude: 151.20)
//        marker.title = "Sydney"
//        marker.snippet = "Australia"
//        marker.map = mapView
    }
}


