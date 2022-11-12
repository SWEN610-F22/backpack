import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-backpack-detail',
  templateUrl: './backpack-detail.component.html',
  styleUrls: ['./backpack-detail.component.scss']
})
export class BackpackDetailComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
  }

}