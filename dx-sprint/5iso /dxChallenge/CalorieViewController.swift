//
//  CalorieViewController.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/16.
//


import UIKit
class CalorieViewController: UIViewController{
    @IBOutlet var calorieLabel: UILabel!
    @IBOutlet var proLabel: UILabel!
    @IBOutlet var carLabel: UILabel!
    @IBOutlet var fatLabel: UILabel!
    @IBOutlet var natLabel: UILabel!
   

    @IBOutlet var proPercentLabel: UILabel!
    @IBOutlet var carPercentLabel: UILabel!
    @IBOutlet var fatPercentLabel: UILabel!
    @IBOutlet var natPercentLabel: UILabel!
    
    var store :StoreList?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        calorieLabel.text = String(store?.calorie ?? 0) + "kcal"
        proLabel.text = String(store?.pro ?? 0) + "g"
        carLabel.text = String(store?.car ?? 0) + "g"
        fatLabel.text = String(store?.fat ?? 0) + "g"
        natLabel.text = String(store?.nat ?? 0) + "mg"
        proLabel.sizeToFit()
        carLabel.sizeToFit()
        fatLabel.sizeToFit()
        natLabel.sizeToFit()
        
        //caloriePercentLabel.text = String(store?.calorie ?? 0) + "kcal"
        proPercentLabel.text = String(format: "%.2f", (store?.pro ?? 0) / 60 * 100) + "%"
        carPercentLabel.text = String(format: "%.2f",(store?.car ?? 0) / 328 * 100) + "%"
        fatPercentLabel.text = String(format: "%.2f",(store?.fat ?? 0) / 50 * 100) + "%"
        natPercentLabel.text = String(format: "%.2f",(store?.nat ?? 0) / 2000 * 100) + "%"
        
       
        
        
    }
}

