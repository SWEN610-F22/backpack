import { Component, OnInit } from '@angular/core';
import { BackPack } from 'src/app/models/backpack.model';
import { BackpackService } from 'src/app/services/backpack.service';

@Component({
  selector: 'app-view-backpacks',
  templateUrl: './view-backpacks.component.html',
  styleUrls: ['./view-backpacks.component.scss']
})
export class ViewBackpacksComponent implements OnInit {
  backpacks:BackPack[] = [];
  constructor(private backpackService:BackpackService) { }

  ngOnInit(): void {
    this.backpackService.getBackpacks().subscribe((backpacks) => this.backpacks = backpacks);
  }

}
