//
//  PlacesViewController.swift
//  dxChallenge
//
//  Created by 김민기 on 2022/08/14.
//


import UIKit
import GoogleMaps
import GooglePlaces
class PlacesViewController: UIViewController{//, CLLocationManagerDelegate {
    var likelyPlaces: [GMSPlace] = []
    var selectedPlace: GMSPlace?
    
    @IBOutlet var tableView: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.delegate = self
        tableView.dataSource = self
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
       if segue.identifier == "unwindToMain" {
         if let nextViewController = segue.destination as? LocationMapViewController {
           nextViewController.selectedPlace = selectedPlace
         }
       }
     }
    
    
}
// Populate the table with the list of most likely places.
extension PlacesViewController: UITableViewDataSource {
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return likelyPlaces.count
  }

  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: "identifier", for: indexPath)
    let collectionItem = likelyPlaces[indexPath.row]

    cell.textLabel?.text = collectionItem.name

    return cell
  }
}
// Respond when a user selects a place.
extension PlacesViewController: UITableViewDelegate {
  func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    selectedPlace = likelyPlaces[indexPath.row]
    performSegue(withIdentifier: "unwindToMain", sender: self)
  }

  // Adjust cell height to only show the first five items in the table
  // (scrolling is disabled in IB).
  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    return self.tableView.frame.size.height/5
  }

  // Make table rows display at proper height if there are less than 5 items.
  func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
    if (section == tableView.numberOfSections - 1) {
      return 1
    }
    return 0
  }
}
