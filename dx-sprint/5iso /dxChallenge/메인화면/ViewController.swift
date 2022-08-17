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
    @IBOutlet var bannerCollection: UICollectionView!
    @IBOutlet weak var tableView: UITableView!
    var stores = [StoreList]()
    var codenm = "rand"
    var sideDishCount = [StoreList]()
    var fruitCount = [StoreList]()
    var breadCount = [StoreList]()
    var ricecakeCount = [StoreList]()
    var searchValue = [StoreList]()
    
    var bannerValue = [BannerList]()
    var nowPage: Int = 0
    var bannerArray: Array<UIImage> = [UIImage(named: "banner_season1.png")!, UIImage(named: "banner_season2.png")!, UIImage(named: "banner_season3.png")!]

    let graycolorLiteral = #colorLiteral(red: 0.9058824182, green: 0.9058824182, blue: 0.9058824182, alpha: 1)
    let orangecolorLiteral = #colorLiteral(red: 0.9987573028, green: 0.7507843375, blue: 0.3068976402, alpha: 1)
    
    override func viewDidLoad() {
        super.viewDidLoad()

        downloadJSON {
            print("success")
            self.tableView.reloadData()
            
        }
        downloadBannerJSON{
            print("success2")
            self.bannerCollection.reloadData()
            
        }
        locationManager.requestWhenInUseAuthorization() // 앱이 실행될 때 위치 추적 권한 요청
        locationManager = CLLocationManager()

        tableView.delegate = self
        tableView.dataSource = self
        
        var frame = CGRect.zero
        frame.size.height = .maximum(0, 15)
        tableView.tableHeaderView = UIView(frame: frame)
        
        bannerCollection.delegate = self
        bannerCollection.dataSource = self
        bannerTimer()
        
        self.setSearchControllerUI()
        [sideButton,fributton,ricebutton,brebutton].forEach {
            
            $0?.layer.cornerRadius = 10
        }
        let backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: self, action: nil)
        backBarButtonItem.tintColor = .gray
        self.navigationItem.backBarButtonItem = backBarButtonItem
        addNavBarImage()
        self.searchBar.searchBarStyle = .minimal
    }
    
    func addNavBarImage() {
            let navController = navigationController!
            let image = UIImage(named: "Main Screen_Logo") 
            let imageView = UIImageView(image: image)
            let bannerWidth = navController.navigationBar.frame.size.width
            let bannerHeight = navController.navigationBar.frame.size.height
            let bannerX = bannerWidth / 2 - (image?.size.width)! / 2
            let bannerY = bannerHeight / 2 - (image?.size.height)! / 2
            //imageView.frame = CGRect(x: 10 , y: 10, width: 1, height: 1)
            imageView.contentMode = .scaleAspectFit
            navigationItem.titleView = imageView
//        navigationItem.rightBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "Main Screen_Location Button"), style: .plain, target: self, action: #selector(tapMapButton))
//        let button = UIButton()
//        button.setImage(UIImage(named: "Main Screen_Location Button"), for: .normal)
//        button.frame = CGRect(x: 0, y: 0, width: 10, height: 10)
        //button.setTitle("설정", for: .normal)
        //button.setTitleColor(.black, for: .normal)
        //button.titleEdgeInsets = UIEdgeInsets(top: 0, left: 10, bottom: 0, right: 0)
//        let barButton = UIBarButtonItem(customView: button)
//        navigationController?.navigationItem.rightBarButtonItem = barButton
//        navigationItem.rightBarButtonItem = UIBarButtonItem(
//            image: UIImage(named: "Main Screen_Location Button"),
//            style: .plain,
//            target: self,
//            action: #selector(tapMapButton(_:))
//        )
        }
    //서치바
    func a(){
        
    }
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
            cell.mainFoodPrice.text = String(store.original_price)+"원"
            cell.mainFoodDisPrice.text = String(store.saled_price)+"원"
            cell.mainStoreName.text = store.store_nm
            cell.mainFoodStock.text = String(store.quanity)+"개 남음"
            cell.mainFoodName.sizeToFit()
            let imgUrl = store.photo_link
            cell.mainFoodPic.downloaded(from: imgUrl)
            cell.maindemaPrice.text = ""
            if store.code_nm == "과일"{
                cell.maindemaPrice.text = "(도매가격:\(store.market_price)원)"
            }
            if store.quanity <= 5{
                cell.mainFoodStock.textColor = .red
            }
         

        } else if codenm == "반찬"{
            let side = sideDishCount[indexPath.section]
            if side.code_nm == "반찬"{
                cell.mainFoodName.text = side.food_nm
                cell.mainFoodPrice.text = String(side.original_price)+"원"
                cell.mainFoodDisPrice.text = String(side.saled_price)+"원"
                cell.mainStoreName.text = side.store_nm
                cell.mainFoodStock.text = String(side.quanity)+"개 남음"
                let imgUrl = side.photo_link
               cell.mainFoodPic.downloaded(from: imgUrl)
                cell.maindemaPrice.text = ""
            }
        } else if codenm == "빵"{
            let bre = breadCount[indexPath.section]
            if bre.code_nm == "빵"{
                cell.mainFoodName.text = bre.food_nm
                cell.mainFoodPrice.text = String(bre.original_price)+"원"
                cell.mainFoodDisPrice.text = String(bre.saled_price)+"원"
                cell.mainStoreName.text = bre.store_nm
                cell.mainFoodStock.text = String(bre.quanity)+"개 남음"
                let imgUrl = bre.photo_link
                cell.mainFoodPic.downloaded(from: imgUrl)
                cell.maindemaPrice.text = ""
            }
        } else if codenm == "과일"{
            let fru = fruitCount[indexPath.section]
            if fru.code_nm == "과일"{
                cell.mainFoodName.text = fru.food_nm
                cell.mainFoodPrice.text = String(fru.original_price)+"원"
                cell.mainFoodDisPrice.text = String(fru.saled_price)+"원"
                cell.mainStoreName.text = fru.store_nm
                cell.mainFoodStock.text = String(fru.quanity)+"개 남음"
                let imgUrl = fru.photo_link
                cell.mainFoodPic.downloaded(from: imgUrl)
                cell.maindemaPrice.text = "(도매가격:\(fru.market_price)원)"
                
            }
        }
         else if codenm == "떡"{
            let rice = ricecakeCount[indexPath.section]
            if rice.code_nm == "떡"{
                cell.mainFoodName.text = rice.food_nm
                cell.mainFoodPrice.text = String(rice.original_price)+"원"
                cell.mainFoodDisPrice.text = String(rice.saled_price)+"원"
                cell.mainStoreName.text = rice.store_nm
                cell.mainFoodStock.text = String(rice.quanity)+"개 남음"
                let imgUrl = rice.photo_link
                cell.mainFoodPic.downloaded(from: imgUrl)
                cell.maindemaPrice.text = ""
            }
         } else if codenm == "검색"{  //검색시에는 서치[store]를 하나 만들어서 찾은 다음에 테이블뷰리로드
             let ser = searchValue[indexPath.section]
                 cell.mainFoodName.text = ser.food_nm
                 cell.mainFoodPrice.text = String(ser.original_price)+"원"
                 cell.mainFoodDisPrice.text = String(ser.saled_price)+"원"
                 cell.mainStoreName.text = ser.store_nm
                 cell.mainFoodStock.text = String(ser.quanity)+"개 남음"
                 let imgUrl = ser.photo_link
                 cell.mainFoodPic.downloaded(from: imgUrl)
                cell.maindemaPrice.text = ""
             if ser.code_nm == "과일"{
                 cell.maindemaPrice.text = "(도매가격:\(ser.market_price)원)"
             }
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
        if codenm == "과일"{
            let nm = fruitCount[indexPath.section]
            performSegue(withIdentifier: "showDomeDetails", sender: self)
            print(nm,1)
        } else if codenm == "검색"{
            let nm = searchValue[indexPath.section]
            if nm.code_nm == "과일"{
                performSegue(withIdentifier: "showDomeDetails", sender: self)
            }else {
                performSegue(withIdentifier: "showDetails", sender: self)
            }
            }
            else {
            let nm = stores[indexPath.section]
            if nm.code_nm == "과일"{
                performSegue(withIdentifier: "showDomeDetails", sender: self)
            } else{
                performSegue(withIdentifier: "showDetails", sender: self)
            }
            
        }
    }
    // 2초마다 실행되는 타이머
    func bannerTimer() {
        let _: Timer = Timer.scheduledTimer(withTimeInterval: 5, repeats: true) { (Timer) in
            self.bannerMove()
        }
    }
    // 배너 움직이는 매서드
    func bannerMove() {
        // 현재페이지가 마지막 페이지일 경우
        if self.nowPage == self.bannerArray.count-1 {
        // 맨 처음 페이지로 돌아감
            self.bannerCollection.scrollToItem(at: NSIndexPath(item: 0, section: 0) as IndexPath, at: .right, animated: true)
            self.nowPage = 0
            return
        }
        // 다음 페이지로 전환
        self.nowPage += 1
        self.bannerCollection.scrollToItem(at: NSIndexPath(item: self.nowPage, section: 0) as IndexPath, at: .centeredHorizontally, animated: true)
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
    func downloadBannerJSON(completed: @escaping () -> ()) {
        let url = URL(string: "http://3.34.139.36:8081/banner")
        
        URLSession.shared.dataTask(with: url!) { data, response, err in
            
            if err == nil {
                do {
                    self.bannerValue = try JSONDecoder().decode([BannerList].self, from: data!)
                    for i in self.bannerValue{
                        if i.banner_code == "rain"{
                            self.bannerArray.append(UIImage(named: "banner_weather_rain.png")!)
                        } else if i.banner_code == "hot"{                            self.bannerArray.append(UIImage(named: "banner_weather_hot.png")!)
                        } else if i.banner_code == "cold"{
                            self.bannerArray.append(UIImage(named: "banner_weather_cold.png")!)
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
        if let destination2 = segue.destination as? LocationMapViewController { //mapbutton
            destination2.store = stores
        }
        if let destination3 = segue.destination as? SecdomeViewController { //mapview
            destination3.store = stores[tableView.indexPathForSelectedRow!.section]
            if codenm == "과일" {
                destination3.store = fruitCount[tableView.indexPathForSelectedRow!.section]
            }else if codenm == "검색"{
                destination3.store = searchValue[tableView.indexPathForSelectedRow!.section]
            }
        }
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
        self.searchBar.showsCancelButton = true
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

extension ViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    //컬렉션뷰 개수 설정
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return bannerArray.count
    }
    
    //컬렉션뷰 셀 설정
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = bannerCollection.dequeueReusableCell(withReuseIdentifier: "BannerCell", for: indexPath) as! BannerCell
        
        cell.bannerImage.image = self.bannerArray[indexPath.row]
        cell.countLabel.layer.cornerRadius = 8
        cell.countLabel.text = "\(self.nowPage + 1)/\(self.bannerArray.count )"
        cell.countLabel.layer.masksToBounds = true
        cell.countLabel.backgroundColor = .gray
        cell.countLabel.textColor = .white

        return cell
    }
    
    // UICollectionViewDelegateFlowLayout 상속
    //컬렉션뷰 사이즈 설정
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
            
            return CGSize(width: bannerCollection.frame.size.width  , height:  bannerCollection.frame.height)
        }
        
    
    //컬렉션뷰 감속 끝났을 때 현재 페이지 체크
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        self.nowPage = Int(scrollView.contentOffset.x) / Int(scrollView.frame.width)
        bannerCollection.reloadData()
    }
    
}
