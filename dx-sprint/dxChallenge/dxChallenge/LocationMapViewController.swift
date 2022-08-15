//
//  LocationMapViewController.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/10.
//


import UIKit
import GoogleMaps
class LocationMapViewController: UIViewController, CLLocationManagerDelegate {
    var locationManager = CLLocationManager()
    var store: [StoreList] = []
    override func viewDidLoad() {
        super.viewDidLoad()
        
        locationManager = CLLocationManager()
        locationManager.delegate = self

        // 배터리에 맞게 권장되는 최적의 정확도
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        // 위치 업데이트
        locationManager.startUpdatingLocation()

        // 위, 경도 가져오기
        let coor = locationManager.location?.coordinate
        let latitude = (coor?.latitude ?? 35.1732211) as Double
        let longitude = (coor?.longitude ?? 129.13045092) as Double

        let camera = GMSCameraPosition.camera(withLatitude: latitude, longitude: longitude, zoom: 16.0)

        let mapView = GMSMapView.map(withFrame: self.view.frame, camera: camera)
        view = mapView
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        marker.title = "현재위치"
        marker.map = mapView
        
        for i in 0..<store.count{

            let marker2 = GMSMarker()
            marker2.position = CLLocationCoordinate2D(latitude: Double(store[i].x)!, longitude: Double(store[i].y)!)
            marker2.title = store[i].store_nm
            marker2.snippet = store[i].address
            marker2.map = mapView
            
            
        }
    }
}


