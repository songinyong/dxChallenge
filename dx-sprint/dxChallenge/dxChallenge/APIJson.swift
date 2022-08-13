//
//  APIJson.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/08.
//

import Foundation
import UIKit
var arr :[Animal] = []
func getAnimalData(){//completion: @escaping (Animal) -> ()) {
  guard let url = URL(string: "http://3.34.139.36:8081/store") else {
    return
  }
  var request = URLRequest(url: url)
  request.httpMethod = "GET"
  let task = URLSession.shared.dataTask(with: request) { data, response, error in
    // error 검증
    if let error = error {
      print("1")
      print(error.localizedDescription)
      return
    }
    //print(response)
    // response status code 검증
    guard let httpResponse = response as? HTTPURLResponse,
       (200..<300).contains(httpResponse.statusCode) else {
        print("2")
         print("error")
         return
       }
    //print(data)
    // data 검증 및 사용
    if let data = data {
      do {
        let receivedData = try JSONDecoder().decode([Animal].self, from: data)
        //completion(receivedData)
        //print(receivedData)
          arr = receivedData
          print(arr[0])
      } catch {
        print("3")
        print(error.localizedDescription)
      }
    }
  }
  task.resume()
}
// }

struct Animal: Codable {
var id: Int
var store_nm: String
var x: String
var y: String
var rating :String
var address:String
//  init(from decoder: Decoder) throws {
//      let values = try decoder.container(keyedBy: CodingKeys.self)
////      id = try values.decodeIfPresent(Int.self, forKey: .id) ?? 1
////    store_nm = try values.decodeIfPresent(String.self, forKey: .store_nm) ?? "디폴트값2"
////    x = try values.decodeIfPresent(Int.self, forKey: .x) ?? 1
////    y = try values.decodeIfPresent(Int.self, forKey: .y) ?? 1
////    rating = try values.decodeIfPresent(Double.self, forKey: .rating) ?? 0.0
////    address = try values.decodeIfPresent(String.self, forKey: .address) ?? "디폴트캅ㄱㅅ"
//    }

}
