//
//  LocationMapViewController.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/10.
//


import UIKit
import GoogleMaps

class LocationMapViewController: UIViewController, CLLocationManagerDelegate {
    
    var store: [StoreList] = []
    var locationManager: CLLocationManager!
    var mapView: GMSMapView!
    
    var preciseLocationZoomLevel: Float = 15.0
    var approximateLocationZoomLevel: Float = 10.0
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        locationManager = CLLocationManager()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.requestWhenInUseAuthorization()
        locationManager.delegate = self
        
        
        let coor = locationManager.location?.coordinate
        let latitude = (coor?.latitude ?? 35.1732211) as Double
        let longitude = (coor?.longitude ?? 129.13045092) as Double
        
        let zoomLevel = locationManager.accuracyAuthorization == .fullAccuracy ? preciseLocationZoomLevel : approximateLocationZoomLevel
        let camera = GMSCameraPosition.camera(withLatitude: latitude,
                                              longitude: longitude,
                                              zoom: zoomLevel)
        mapView = GMSMapView.map(withFrame: view.bounds, camera: camera)
        mapView.settings.myLocationButton = true
        mapView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        mapView.isMyLocationEnabled = true
        
        view.addSubview(mapView)
        
        //view = mapView
        for i in 0..<store.count{
            
            let marker2 = GMSMarker()
            marker2.position = CLLocationCoordinate2D(latitude: Double(store[i].x)!, longitude: Double(store[i].y)!)
            marker2.title = store[i].store_nm
            marker2.snippet = store[i].address
            marker2.map = mapView
            
            
        }
        
        
    }
}

