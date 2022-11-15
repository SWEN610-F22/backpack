import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-search-backpacks-bar',
  templateUrl: './search-backpacks-bar.component.html',
  styleUrls: ['./search-backpacks-bar.component.scss']
})
export class SearchBackpacksBarComponent implements OnInit {

  @Output() onSearchBackpacks: EventEmitter<string> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  onSearch(searchString : string){
    this.onSearchBackpacks.emit(searchString);
  }

}