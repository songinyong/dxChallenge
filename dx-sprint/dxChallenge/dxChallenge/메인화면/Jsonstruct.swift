//
//  Jsonstruct.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/10.
//

import Foundation

struct StoreList: Codable {

    let stock_id:Int
    let food_nm:String
    let photo_link:String
    let original_price:Int
    let saled_price:Int
    let quanity:Int
    let expiration_date:String
    
    let store_id:Int
    let store_nm: String
    let x: String
    let y: String
    let rating :String
    let address:String
    let code_nm:String
    let opening_hour:String
    
}
