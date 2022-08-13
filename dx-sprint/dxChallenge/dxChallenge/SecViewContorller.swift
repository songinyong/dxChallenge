//
//  SecViewContorller.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/10.
//

import UIKit

extension UIImageView {
    func downloaded(from url: URL, contentMode mode: ContentMode = .scaleToFill) {
        contentMode = mode
        URLSession.shared.dataTask(with: url) { data, response, error in
            guard
                let httpURLResponse = response as? HTTPURLResponse, httpURLResponse.statusCode == 200,
                let mimeType = response?.mimeType, mimeType.hasPrefix("image"),
                let data = data, error == nil,
                let image = UIImage(data: data)
                else {return }
            DispatchQueue.main.async() { [weak self] in
                self?.image = image
            }
        }.resume()
    }
    func downloaded(from link: String, contentMode mode: ContentMode = .scaleToFill) {
        guard let url = URL(string: link) else { return }
        downloaded(from: url, contentMode: mode)
    }
}

class SecViewController: UIViewController {

    @IBOutlet weak var foodname: UILabel!
    @IBOutlet weak var storename: UILabel!
    @IBOutlet weak var storeaddress: UILabel!
    @IBOutlet weak var foodImg: UIImageView!
    
    @IBOutlet var infoVIew: UIView!
    @IBOutlet weak var FoodPrice: UILabel!
    @IBOutlet weak var FoodDisPrice: UILabel!
    @IBOutlet weak var FoodStock: UILabel!
    @IBOutlet var closeTime: UILabel!
    
    
    
    var store: StoreList?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        infoVIew.clipsToBounds = false
        infoVIew.layer.cornerRadius = 15
        infoVIew.layer.shadowOpacity = 0.5
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

        foodImg.downloaded(from: imgUrl)
        
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destination2 = segue.destination as? MapViewController { //mapview
            destination2.store = store
        }
    }
}

extension String {
    func strikeThrough() -> NSAttributedString {
        let attributeString = NSMutableAttributedString(string: self)
        attributeString.addAttribute(NSAttributedString.Key.strikethroughStyle, value: NSUnderlineStyle.single.rawValue, range: NSMakeRange(0, attributeString.length))
        return attributeString
    }
}
