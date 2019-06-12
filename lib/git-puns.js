'use babel';

import GitPunsView from './git-puns-view';
import { CompositeDisposable } from 'atom';

export default {

  gitPunsView: null,
  modalPanel: null,
  subscriptions: null,

  activate(state) {
    this.gitPunsView = new GitPunsView(state.gitPunsViewState);
    this.modalPanel = atom.workspace.addModalPanel({
      item: this.gitPunsView.getElement(),
      visible: false
    });

    // Events subscribed to in atom's system can be easily cleaned up with a CompositeDisposable
    this.subscriptions = new CompositeDisposable();

    // Register command that toggles this view
    this.subscriptions.add(atom.commands.add('atom-workspace', {
      'git-puns:toggle': () => this.toggle()
    }));
  },

  deactivate() {
    this.modalPanel.destroy();
    this.subscriptions.dispose();
    this.gitPunsView.destroy();
  },

  serialize() {
    return {
      gitPunsViewState: this.gitPunsView.serialize()
    };
  },

  toggle() {
    console.log('GitPuns was toggled!');
    return (
      this.modalPanel.isVisible() ?
      this.modalPanel.hide() :
      this.modalPanel.show()
    );
  }

};
