//
//  ViewController.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/07.
//


import UIKit
import GoogleMaps

class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    var locationManager = CLLocationManager()
    
    @IBOutlet var ricebutton: UIButton!
    @IBOutlet var brebutton: UIButton!
    @IBOutlet var fributton: UIButton!
    @IBOutlet var sideButton: UIButton!
    
    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var tableView: UITableView!
    var stores = [StoreList]()
    var codenm = "rand"
    var sideDishCount = [StoreList]()
    var fruitCount = [StoreList]()
    var breadCount = [StoreList]()
    var ricecakeCount = [StoreList]()
    var searchValue = [StoreList]()
    let graycolorLiteral = #colorLiteral(red: 0.9058824182, green: 0.9058824182, blue: 0.9058824182, alpha: 1)
    let orangecolorLiteral = #colorLiteral(red: 1, green: 0.625218749, blue: 0.3162121773, alpha: 1)
    
    override func viewDidLoad() {
        super.viewDidLoad()

        downloadJSON {
            print("success")
            self.tableView.reloadData()
            
        }
        locationManager = CLLocationManager()
        locationManager.requestWhenInUseAuthorization() // 앱이 실행될 때 위치 추적 권한 요청
        tableView.delegate = self
        tableView.dataSource = self
        self.setSearchControllerUI()
        [sideButton,fributton,ricebutton,brebutton].forEach {
            
            $0?.layer.cornerRadius = 15
        }
        let backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: self, action: nil)
        backBarButtonItem.tintColor = .gray
        self.navigationItem.backBarButtonItem = backBarButtonItem
        addNavBarImage()
        
    }
    
    func addNavBarImage() {
            let navController = navigationController!
            let image = UIImage(named: "Main Screen_Logo") 
            let imageView = UIImageView(image: image)
            let bannerWidth = navController.navigationBar.frame.size.width
            let bannerHeight = navController.navigationBar.frame.size.height
            let bannerX = bannerWidth / 2 - (image?.size.width)! / 2
            let bannerY = bannerHeight / 2 - (image?.size.height)! / 2
            imageView.frame = CGRect(x: bannerX, y: bannerY, width: bannerWidth, height: bannerHeight)
            imageView.contentMode = .scaleAspectFit
            navigationItem.titleView = imageView
        }
    //서치바
    func setSearchControllerUI() {
            self.searchBar.delegate = self
            self.searchBar.showsCancelButton = false
        }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "CustomCell") as! CustomCell
        cell.mainFoodPrice.attributedText = cell.mainFoodPrice.text?.strikeThrough()
        //cell.selectionStyle = .none
        if codenm == "rand" {
            let store = stores[indexPath.section]
            cell.mainFoodName.text = store.food_nm
            cell.mainFoodPrice.text = String(store.original_price)
            cell.mainFoodDisPrice.text = String(store.saled_price)
            cell.mainStoreName.text = store.store_nm
            cell.mainFoodStock.text = String(store.quanity)+"개 남음"
            cell.mainFoodName.sizeToFit()
            let imgUrl = store.photo_link
            cell.mainFoodPic.downloaded(from: imgUrl)

        } else if codenm == "반찬"{
            let side = sideDishCount[indexPath.section]
            if side.code_nm == "반찬"{
                cell.mainFoodName.text = side.food_nm
                cell.mainFoodPrice.text = String(side.original_price)
                cell.mainFoodDisPrice.text = String(side.saled_price)
                cell.mainStoreName.text = side.store_nm
                cell.mainFoodStock.text = String(side.quanity)
                let imgUrl = side.photo_link
               cell.mainFoodPic.downloaded(from: imgUrl)
            }
        } else if codenm == "빵"{
            let bre = breadCount[indexPath.section]
            if bre.code_nm == "빵"{
                cell.mainFoodName.text = bre.food_nm
                cell.mainFoodPrice.text = String(bre.original_price)
                cell.mainFoodDisPrice.text = String(bre.saled_price)
                cell.mainStoreName.text = bre.store_nm
                cell.mainFoodStock.text = String(bre.quanity)
                let imgUrl = bre.photo_link
                cell.mainFoodPic.downloaded(from: imgUrl)
            }
        } else if codenm == "과일"{
            let fru = fruitCount[indexPath.section]
            if fru.code_nm == "과일"{
                cell.mainFoodName.text = fru.food_nm
                cell.mainFoodPrice.text = String(fru.original_price)
                cell.mainFoodDisPrice.text = String(fru.saled_price)
                cell.mainStoreName.text = fru.store_nm
                cell.mainFoodStock.text = String(fru.quanity)
                let imgUrl = fru.photo_link
                cell.mainFoodPic.downloaded(from: imgUrl)
            }
        }
         else if codenm == "떡"{
            let rice = ricecakeCount[indexPath.section]
            if rice.code_nm == "떡"{
                cell.mainFoodName.text = rice.food_nm
                cell.mainFoodPrice.text = String(rice.original_price)
                cell.mainFoodDisPrice.text = String(rice.saled_price)
                cell.mainStoreName.text = rice.store_nm
                cell.mainFoodStock.text = String(rice.quanity)
                let imgUrl = rice.photo_link
                cell.mainFoodPic.downloaded(from: imgUrl)
            }
         } else if codenm == "검색"{  //검색시에는 서치[store]를 하나 만들어서 찾은 다음에 테이블뷰리로드
             let ser = searchValue[indexPath.section]
                 cell.mainFoodName.text = ser.food_nm
                 cell.mainFoodPrice.text = String(ser.original_price)
                 cell.mainFoodDisPrice.text = String(ser.saled_price)
                 cell.mainStoreName.text = ser.store_nm
                 cell.mainFoodStock.text = String(ser.quanity)
                 let imgUrl = ser.photo_link
                 cell.mainFoodPic.downloaded(from: imgUrl)
          }

        return cell
    }
    func numberOfSections(in tableView: UITableView) -> Int {
        if codenm == "반찬"{
            return sideDishCount.count
        } else if codenm == "빵"{
            return breadCount.count
        }  else if codenm == "떡"{
            return ricecakeCount.count
        }  else if codenm == "과일"{
            return fruitCount.count
        } else if codenm == "검색"{
            return searchValue.count
        }
        return stores.count
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        performSegue(withIdentifier: "showDetails", sender: self)
    }
    
    
    func downloadJSON(completed: @escaping () -> ()) {
        let url = URL(string: "http://3.34.139.36:8081/stockin")
        
        URLSession.shared.dataTask(with: url!) { data, response, err in
            
            if err == nil {
                do {
                    self.stores = try JSONDecoder().decode([StoreList].self, from: data!)
                    //디코딩한거 각각의 배열에 넣어주기 ->
                    for i in self.stores{
                        if i.code_nm == "떡"{
                            self.ricecakeCount.append(i)
                        } else if i.code_nm == "빵"{
                            self.breadCount.append(i)
                        } else if i.code_nm == "과일"{
                            self.fruitCount.append(i)
                        } else if i.code_nm == "반찬"{
                            self.sideDishCount.append(i)
                        }
                    }
                    DispatchQueue.main.async {
                        completed()
                    }
                }
                catch {
                    print("error fetching data from api")
                }
            }
        }.resume()
    }
    
    @IBAction func sideDishButton(_ sender: UIButton) { //반찬
        if self.codenm == "반찬"{
            self.codenm = "rand"
            sideButton.backgroundColor = graycolorLiteral
            self.tableView.reloadData()
        } else {
            self.codenm = "반찬"
            changegraycolor()
            sideButton.backgroundColor = orangecolorLiteral
            self.tableView.reloadData()
        }
    }
    
    @IBAction func fruitButton(_ sender: UIButton) { //과일
        if self.codenm == "과일"{
            self.codenm = "rand"
            fributton.backgroundColor = graycolorLiteral
            self.tableView.reloadData()
        } else {
            self.codenm = "과일"
            changegraycolor()
            fributton.backgroundColor = orangecolorLiteral
            self.tableView.reloadData()
        }
    }
    @IBAction func breadButton(_ sender: UIButton) { //빵
        if self.codenm == "빵"{
            self.codenm = "rand"
            brebutton.backgroundColor = graycolorLiteral
            self.tableView.reloadData()
        } else {
            self.codenm = "빵"
            changegraycolor()
            brebutton.backgroundColor = orangecolorLiteral
            self.tableView.reloadData()
        }
        
        
    }
    @IBAction func riceCake(_ sender: UIButton) { //밥
        if self.codenm == "떡"{
            self.codenm = "rand"
            ricebutton.backgroundColor = graycolorLiteral
            self.tableView.reloadData()
        } else {
            self.codenm = "떡"
            changegraycolor()
            ricebutton.backgroundColor = orangecolorLiteral
            self.tableView.reloadData()
        }
        
        
        
    }
    @IBAction func tapMapButton(_ sender: UIButton) {
        performSegue(withIdentifier: "showmap", sender: self)
        
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destination = segue.destination as? SecViewController { //secview 호출
            if codenm == "떡" {
                destination.store = ricecakeCount[tableView.indexPathForSelectedRow!.section]
            } else if codenm == "과일" {
                destination.store = fruitCount[tableView.indexPathForSelectedRow!.section]
            }else if codenm == "빵" {
                destination.store = breadCount[tableView.indexPathForSelectedRow!.section]
            }else if codenm == "반찬" {
                destination.store = sideDishCount[tableView.indexPathForSelectedRow!.section]
            }else if codenm == "검색" {
                destination.store = searchValue[tableView.indexPathForSelectedRow!.section]
            } else {
                destination.store = stores[tableView.indexPathForSelectedRow!.section]
            }
        }
//        if let destination2 = segue.destination as? LocationMapViewController { //mapbutton
//            destination2.store = stores
//        }
    }
    func changegraycolor(){
        sideButton.backgroundColor = graycolorLiteral
        ricebutton.backgroundColor = graycolorLiteral
        brebutton.backgroundColor = graycolorLiteral
        fributton.backgroundColor = graycolorLiteral
        
    }
    //화면터치시 키보드 내리기
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?){       self.view.endEditing(true) }
   
}

extension ViewController: UISearchBarDelegate {
    // 서치바에서 검색을 시작할 때 호출
   
    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        //self.isFiltering = true
        self.searchBar.showsCancelButton = true
        //self.tableView.reloadData()
    }
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        //guard let text  = searchBar.text?.lowercased() else { return }
    }
    
    // 서치바에서 검색버튼을 눌렀을 때 호출
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        dismissKeyboard()
        codenm = "검색"
        searchValue.removeAll()
        guard let text = searchBar.text?.lowercased() else { return }
        for i in stores{
            if i.food_nm == text{
                searchValue.append(i)
            }
        }
        self.tableView.reloadData()
        changegraycolor()

    }
    
    // 서치바에서 취소 버튼을 눌렀을 때 호출
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        self.searchBar.text = ""
        self.searchBar.resignFirstResponder()
        //self.isFiltering = false
        codenm = "rand"
        self.tableView.reloadData()
        self.searchBar.showsCancelButton = false
    }
    
    // 서치바 검색이 끝났을 때 호출
    func searchBarTextDidEndEditing(_ searchBar: UISearchBar) {
        changegraycolor()
        self.tableView.reloadData()
    }
    
    // 서치바 키보드 내리기
    func dismissKeyboard() {
        searchBar.resignFirstResponder()
    }
}

