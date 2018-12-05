import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Location, LocationsService } from "../services/locations.service";
import { delay } from 'rxjs/operators';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {
  private id: number;
  private name: string;
  private longitude: number;
  private latitude: number;
  private description: string;

  data: Location[];


  constructor(private http:HttpClient, private svc: LocationsService) { }

  ngOnInit() {
    this.GetLocations();
  }
  
  GetLocations = () => {
    this.svc.getLocations().subscribe(data => {this.data = data;});
  }

  postLocation(){
    this.http.post('https://cluego.azurewebsites.net/api/location', {
      locName: this.name,
      locDescription: this.description,
      locLat: this.latitude,
      locLong: this.longitude,
    })
    .subscribe((data: any)=>{
    })
    setTimeout(
      function(){ 
      location.reload(); 
      }, 2000);

    //window.location.reload();
  }

  DeleteLoc = (location) =>
  {
    this.http.delete(`https://cluego.azurewebsites.net/api/location/delete/${location.locId}`)
    window.open(`https://cluego.azurewebsites.net/api/location/delete/${location.locId}`)
    setTimeout(
      function(){ 
      window.location.reload(); 
      }, 2000);
  }

  get Id() {
    return this.id;
  }
  set Id(value: number) {
    this.id = value;
  }
  get Name() {
    return this.name;
  }
  set Name(value: string) {
    this.name = value;
  }
  get Latitude() {
    return this.latitude;
  }
  set Latitude(value: number) {
    this.latitude = value;
  }
  get Description() {
    return this.description;
  }
  set Description(value: string) {
    this.description = value;
  }
  get Longitude() {
    return this.longitude;
  }
  set Longitude(value: number) {
    this.longitude = value;
  }
}
