//
//  SecdomeViewController.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/16.
//

//import Foundation
import UIKit
class SecdomeViewController: UIViewController {

    @IBOutlet weak var foodname: UILabel!
    @IBOutlet weak var storename: UILabel!
    @IBOutlet weak var storeaddress: UILabel!
    @IBOutlet weak var foodImg: UIImageView!

    @IBOutlet var infoVIew: UIView!
    @IBOutlet weak var FoodPrice: UILabel!
    @IBOutlet weak var FoodDisPrice: UILabel!
    @IBOutlet weak var FoodStock: UILabel!
    @IBOutlet var closeTime: UILabel!
    @IBOutlet var domaPrice: UILabel!
    @IBOutlet var calorieViewButton: UIButton!
//
    
    var store: StoreList?
//
    override func viewDidLoad() {
        super.viewDidLoad()
//
        infoVIew.clipsToBounds = false
        infoVIew.layer.cornerRadius = 15
        infoVIew.layer.shadowOpacity = 0.3
        infoVIew.layer.shadowColor = UIColor.black.cgColor
        infoVIew.layer.shadowOffset = CGSize(width: 0, height: 0)
        infoVIew.layer.shadowRadius = 5


        self.navigationItem.title = store?.store_nm
        foodname.text = store?.food_nm
        FoodPrice.text = String(store?.original_price ?? 10000) + "원"
        FoodPrice.attributedText = FoodPrice.text?.strikeThrough()
        FoodDisPrice.text = String(store?.saled_price ?? 5000) + "원"
        FoodStock.text = String(store?.quanity ??  1) + "개 남음"
        storename.text = "영업시간 : \((store?.opening_hour ?? "09:30~22:00" ))"
        storeaddress.text = store?.address
        closeTime.text = store?.expiration_date
        let imgUrl = store?.photo_link ?? ""
        domaPrice.text = "도매가:\(String(store?.market_price ?? 0))원"
        foodImg.downloaded(from: imgUrl)
        calorieViewButton.layer.cornerRadius = 15

        let backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: self, action: nil)
        backBarButtonItem.tintColor = .gray
        self.navigationItem.backBarButtonItem = backBarButtonItem


    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destination = segue.destination as? CalorieViewController { //mapview
            destination.store = store
        }
        if let destination2 = segue.destination as? MapViewController { //mapview
            destination2.store = store
        }
    }
    @IBAction func calorieViewButton(_ sender: UIButton) {
        performSegue(withIdentifier: "showcalorie2", sender: self)
    }
}


