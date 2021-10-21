const dataInput = [
  {
    sample: 'Xin chào nhé!',
    intent: 'greeting',
  },
  {
    sample: 'Chào bạn nha???!!!',
    intent: 'greeting',
  },
  {
    sample: 'Chào',
    intent: 'greeting',
  },
  {
    sample: 'Hi',
    intent: 'greeting',
  },
  {
    sample: 'Hello',
    intent: 'greeting',
  },
  {
    sample: 'Bạn thật xấu xa',
    intent: 'bad',
  },
  {
    sample: 'Bạn thật tệ hại',
    intent: 'bad',
  },
];

const stopWords = ['nha', 'nhé'];

module.exports = {
  dataInput,
  stopWords,
};
