//
//  MapViewController.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/07.
//

import UIKit
import GoogleMaps
class MapViewController: UIViewController, CLLocationManagerDelegate{

    var mapView: GMSMapView!
    var store: StoreList?
    override func viewDidLoad() {
        super.viewDidLoad()

        let latitude = Double( store?.x ?? "35.1732211")!
        let longitude = Double(store?.y ?? "129.13045092")!

        let camera = GMSCameraPosition.camera(withLatitude: latitude,longitude: longitude,zoom: 16.0)
        mapView = GMSMapView.map(withFrame: view.bounds, camera: camera)
        mapView.autoresizingMask = [.flexibleWidth, .flexibleHeight]

        view.addSubview(mapView)
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        marker.title = store?.store_nm
        marker.snippet = store?.address
        marker.map = mapView
        
    }
}


