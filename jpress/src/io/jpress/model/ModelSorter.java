/**
 * Copyright (c) 2015-2016, Michael Yang 杨福海 (fuhai999@gmail.com).
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ModelSorter {

	public static <M extends ISortModel> void sort(List<M> tlist) {
		if (tlist == null)
			return;

		List<M> newList = new ArrayList<M>();
		sort(tlist, newList, (long) 0, 0);
		tlist.clear();
		tlist.addAll(newList);
	}

	private static <M extends ISortModel> void sort(List<M> tlist, List<M> newlist, Long parentId, int layer) {
		for (M model : tlist) {
			if (parentId == null || parentId == 0) {
				if (model.getParentId() == null || model.getParentId() == 0) {
					model.setLayer(0);// 为顶层分类
					newlist.add(model);
					sort(tlist, newlist, model.getId(), 0);
				}
			} else {
				if (parentId == model.getParentId()) {
					model.setLayer(layer + 1);
					newlist.add(model);
					sort(tlist, newlist, model.getId(), layer + 1);
				}
			}
		}
	}

	public static <M extends ISortModel> void tree(List<M> tlist) {
		if (tlist == null)
			return;

		List<M> newList = new ArrayList<M>();
		tree(tlist, newList, null);
		tlist.clear();
		tlist.addAll(newList);
	}

	private static <M extends ISortModel> void tree(List<M> tlist, List<M> newlist, M parent) {
		for (M model : tlist) {
			if (parent == null) {
				if (model.getParentId() == null || model.getParentId() == 0) {
					newlist.add(model);
					tree(tlist, newlist, model);
				}
			} else {
				if (parent.getId() == model.getParentId()) {
					model.setParent(parent);
					parent.addChild(model);
					tree(tlist, null, model);
				}
			}
		}
	}

	public static interface ISortModel<M extends ISortModel> {
		public void setLayer(int layer);

		public java.lang.Long getId();

		public java.lang.Long getParentId();

		public void setParent(M parent);

		public void addChild(M child);
	}

}
