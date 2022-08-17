//
//  CustomCell.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/10.
//

import UIKit

class CustomCell: UITableViewCell {
    
    @IBOutlet weak var mainFoodPic: UIImageView!
    @IBOutlet weak var mainFoodName: UILabel!
    @IBOutlet weak var mainFoodPrice: UILabel!
    @IBOutlet weak var mainFoodDisPrice: UILabel!
    @IBOutlet weak var mainStoreName: UILabel!
    @IBOutlet weak var mainFoodStock: UILabel!
    @IBOutlet var maindemaPrice: UILabel!
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    override func awakeFromNib() {
        super.awakeFromNib()
    }
}
